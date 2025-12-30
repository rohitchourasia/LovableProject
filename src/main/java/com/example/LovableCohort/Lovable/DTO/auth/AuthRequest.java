package com.example.LovableCohort.Lovable.DTO.auth;

import lombok.Builder;

@Builder
public record AuthRequest(
        String token,
        UserProfileResponse user
) {
}
