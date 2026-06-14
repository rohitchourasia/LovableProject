package com.example.LovableCohort.Lovable.DTO.auth;

import lombok.Builder;

@Builder
public record ChatRequest(
        String message,
        Long projectId
) {
}

