package com.example.LovableCohort.Lovable.Entity;

import com.example.LovableCohort.Lovable.Enums.PreviewStatus;
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
public class Preview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @JoinColumn(unique = true)

    Project project;

    String namespace;
    String podName;
    String previewUrl;

    PreviewStatus status;

    Instant startedAt;
    Instant terminatedAt;

    Instant createdAt;

}

