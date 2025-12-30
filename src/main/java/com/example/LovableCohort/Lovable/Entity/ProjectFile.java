package com.example.LovableCohort.Lovable.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.security.CodeSigner;
import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn
    Project project;
    @Column(unique = true)
    String path;

    String minioObjectKey;

    Instant createdAt;

    Instant updatedAt;

    User createdBy;

    User updatedBy;

}

