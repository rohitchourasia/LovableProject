package com.example.LovableCohort.Lovable.mapper;

import com.example.LovableCohort.Lovable.DTO.Chat.ChatResponse;
import com.example.LovableCohort.Lovable.Entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    List<ChatResponse> chatMessagetoChatResponse(List<ChatMessage>chatResponses);
}
