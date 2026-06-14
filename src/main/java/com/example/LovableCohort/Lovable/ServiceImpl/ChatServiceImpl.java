package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.CompositeKeys.ChatSessionId;
import com.example.LovableCohort.Lovable.DTO.Chat.ChatResponse;
import com.example.LovableCohort.Lovable.Entity.ChatMessage;
import com.example.LovableCohort.Lovable.Entity.ChatSession;
import com.example.LovableCohort.Lovable.Jwt.JwtAuthImpl;
import com.example.LovableCohort.Lovable.Repository.ChatMessageRepository;
import com.example.LovableCohort.Lovable.Repository.ChatSessionRepository;
import com.example.LovableCohort.Lovable.Services.ChatServices;
import com.example.LovableCohort.Lovable.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatServices {
    private final JwtAuthImpl authUtil;
    private final ChatSessionRepository chatSessionRepository ;
    private final ChatMessageRepository chatMessageRepository ;
    private final ChatMapper chatMapper ;


    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {

        Long userId = authUtil.getUserId();

        ChatSession chatSession = chatSessionRepository.getReferenceById(
                new ChatSessionId(projectId, userId)
        );

        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatSession(chatSession);

        return chatMapper.chatMessagetoChatResponse(chatMessageList);
    }
}
