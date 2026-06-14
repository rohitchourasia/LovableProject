package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.CompositeKeys.ChatSessionId;
import com.example.LovableCohort.Lovable.Entity.*;
import com.example.LovableCohort.Lovable.Enums.ChatEventType;
import com.example.LovableCohort.Lovable.Enums.MessageRole;
import com.example.LovableCohort.Lovable.Jwt.JwtAuthImpl;
import com.example.LovableCohort.Lovable.LLM.CodeGenerationTools;
import com.example.LovableCohort.Lovable.LLM.FileTreeContextAdvisor;
import com.example.LovableCohort.Lovable.LLM.LlmResponseParser;
import com.example.LovableCohort.Lovable.LLM.PromptUtil;
import com.example.LovableCohort.Lovable.Repository.*;
import com.example.LovableCohort.Lovable.Services.AiGenerationService;
import com.example.LovableCohort.Lovable.Services.FileService;
import com.example.LovableCohort.Lovable.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service implementation managing AI-driven code generation and automated file persistence.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AiGenerationServiceImpl implements AiGenerationService {

    private final JwtAuthImpl authUtil;
    private final ChatClient chatClient;
    private final FileTreeContextAdvisor fileTreeContextAdvisor;
    private final FileService projectServiceImpl;
    private final ChatSessionRepository chatSessionRepository ;
    private final ProjectRepository projectRepository ;
    private final UserRepository userRepository ;
    private final LlmResponseParser llmResponseParser;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatEventRepository chatEventRepository ;
    private final FileService fileService ;

    /**
     * Regex pattern to extract generated file pathways and contents from the LLM markdown response.
     * Expected contract format: <file path="relative/path/to/file">content</file>
     */
    private static final Pattern FILE_TAG_PATTERN =
            Pattern.compile("<file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);

    @Override
    @PreAuthorize("@security.checkEditAccessibilty(#projectId)")
    public Flux<String> streamResponse(String userMessage, Long projectId) {
        Long userId = authUtil.getUserId();
        ChatSession chatSession = createChatSessionIfNotExists(projectId, userId);

        Map<String, Object> advisorParams = Map.of(
                "userId", userId,
                "projectId", projectId
        );

        // Buffer to accumulate individual chunks for post-stream regex parsing
        StringBuilder responseBuffer = new StringBuilder();

        // Contextual tooling setup for code generation tasks
        CodeGenerationTools codeGenerationTools = new CodeGenerationTools(projectServiceImpl, projectId);

        return chatClient.prompt()
                .system(PromptUtil.CODE_GENERATION_SYSTEM_PROMPT)
                .user(userMessage)
                .advisors(spec -> {
                    spec.params(advisorParams);
                    spec.advisors(fileTreeContextAdvisor);
                })
                .tools(codeGenerationTools)
                .stream()
                .chatResponse()
                // FIX: Extract text content from the ChatResponse object before appending to the buffer
                .doOnNext(response -> {
                    if (response.getResult() != null && response.getResult().getOutput() != null) {
                        String text = response.getResult().getOutput().getText();
                        if (text != null) {
                            responseBuffer.append(text);
                        }
                    }
                })
                // Offload heavy CPU regex parsing and blocking I/O persistence to a bounded elastic pool
                .doOnComplete(() -> Schedulers.boundedElastic().schedule(() ->
                       // parseAndSaveFiles(responseBuffer.toString(), projectId)
                        finalizeChats(userMessage,chatSession,responseBuffer.toString())
                ))
                .doOnError(error -> log.error("AI generation failed for projectId={}", projectId, error))
                // Downstream clients require raw content stream text only
                .map(response -> Objects.requireNonNull(
                        response.getResult().getOutput().getText()
                ));
    }

    /**
     * Initializes a chat session if non-existent to maintain conversation history and context.
     */
    public ChatSession createChatSessionIfNotExists(Long projectId, Long userId) {
        // TODO: Implement persistent session tracking or memory restoration logic
        ChatSessionId chatSessionId = new ChatSessionId(projectId, userId);
        ChatSession chatSession = chatSessionRepository.findById(chatSessionId).orElse(null);

        if(chatSession == null) {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));

            chatSession = ChatSession.builder()
                    .id(chatSessionId)
                    .project(project)
                    .user(user)
                    .build();

            chatSession = chatSessionRepository.save(chatSession);
        }
        return chatSession;
    }
    private void finalizeChats(String userMessage, ChatSession chatSession, String fullText) {
        Long projectId = chatSession.getProject().getId();


        // Save the User message
        chatMessageRepository.save(
                ChatMessage.builder()
                        .chatSession(chatSession)
                        .role(MessageRole.USER)
                        .content(userMessage)
                        .build()
        );

        ChatMessage assistantChatMessage = ChatMessage.builder()
                .role(MessageRole.ASSISTANT)
                .content("Assistant Message here...")
                .chatSession(chatSession)
                .build();
        List<ChatEvent> chatEventList = llmResponseParser.parseChatEvents(fullText, assistantChatMessage);

        chatEventList.stream()
                .filter(e -> e.getType() == ChatEventType.FILE_EDIT)
                .forEach(e -> fileService.saveFile(projectId, e.getFilePath(), e.getContent()));

        chatMessageRepository.save(assistantChatMessage);
    }

    /**
     * Parses the accumulated LLM response via regex tags and persists the extracted files.
     */
    public void parseAndSaveFiles(String fullResponse, Long projectId) {
        Matcher matcher = FILE_TAG_PATTERN.matcher(fullResponse);

        while (matcher.find()) {
            String filePath = matcher.group(1);
            String fileContent = matcher.group(2).trim();

            try {
                projectServiceImpl.saveFile(projectId, filePath, fileContent);
                log.info("Successfully persisted generated file: {} for project: {}", filePath, projectId);
            } catch (Exception e) {
                log.error("Failed to persist file: {} for project: {}", filePath, projectId, e);
            }
        }
    }
}

