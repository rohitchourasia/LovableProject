package com.example.LovableCohort.Lovable.Entity;


import com.example.LovableCohort.Lovable.Enums.MessageRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

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
    @OneToMany(mappedBy = "chatMessage",cascade = {CascadeType.ALL})
    List<ChatEvent> eventList ;

    Integer tokensUsed;

    Instant createdAt;
}

