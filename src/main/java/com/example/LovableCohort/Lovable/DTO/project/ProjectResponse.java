package com.example.LovableCohort.Lovable.DTO.project;

import com.example.LovableCohort.Lovable.DTO.auth.UserProfileResponse;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt,
        UserProfileResponse owner
) {
}
