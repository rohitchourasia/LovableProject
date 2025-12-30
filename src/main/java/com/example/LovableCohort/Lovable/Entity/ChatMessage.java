package com.example.LovableCohort.Lovable.Entity;


import com.example.LovableCohort.Lovable.Enums.MessageRole;
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
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn
    ChatSession chatSession;

    String content;

    MessageRole role;

    String toolCalls; // JSON Array of Tools Called

    Integer tokensUsed;

    Instant createdAt;
}

