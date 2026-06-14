package com.example.LovableCohort.Lovable.DTO.Chat;

import java.time.Instant;
import java.util.List;

public record ChatResponse (

            Long id,

            String content,
            Integer tokensUsed,
            Instant createdAt

){}
