package com.example.LovableCohort.Lovable.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsageLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn
    User user;
    @ManyToOne
    @JoinColumn
    Project project;

    String action;

    Integer tokensUsed;
    Integer durationMs;

    String metaData; // JSON of {model_used, prompt_used},

    Instant createdAt;
}

