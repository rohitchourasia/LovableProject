package com.example.LovableCohort.Lovable.Services;

import reactor.core.publisher.Flux;

public interface AiGenerationService {
    Flux<String> streamResponse(String message, Long projectId);
}
