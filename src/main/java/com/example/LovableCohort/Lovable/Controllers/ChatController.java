package com.example.LovableCohort.Lovable.Controllers;

import com.example.LovableCohort.Lovable.DTO.Chat.ChatResponse;
import com.example.LovableCohort.Lovable.DTO.auth.ChatRequest;
import com.example.LovableCohort.Lovable.Services.AiGenerationService;
import com.example.LovableCohort.Lovable.Services.ChatServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final AiGenerationService aiGenerationService;
    private final ChatServices chatService;
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(
            @RequestBody ChatRequest request) {

        return aiGenerationService.streamResponse(request.message(), request.projectId());

    }
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<ChatResponse>> getChatHistory(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(chatService.getProjectChatHistory(projectId));
    }



}
