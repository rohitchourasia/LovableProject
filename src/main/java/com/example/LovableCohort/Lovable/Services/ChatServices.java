package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.Chat.ChatResponse;

import java.util.List;

public interface ChatServices {
    List<ChatResponse> getProjectChatHistory(Long projectId);
}
