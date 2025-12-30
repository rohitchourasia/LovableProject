package com.example.LovableCohort.Lovable.Entity;

import com.example.LovableCohort.Lovable.CompositeKeys.ChatSessionId;
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
public class ChatSession {
    @EmbeddedId
    ChatSessionId id;
    @MapsId("projectId")
    @ManyToOne
    @JoinColumn
    Project project;
    @MapsId("userId")
    @ManyToOne
    @JoinColumn
    User user;

    String title;

    Instant createdAt;
    Instant updatedAt;

    Instant deletedAt; //soft delete
}
