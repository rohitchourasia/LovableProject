package com.example.LovableCohort.Lovable.DTO.project;

import java.time.Instant;

public record FileNode(
        String path,
        Instant modifiedAt,
        Long size,
        String type
) {
}
