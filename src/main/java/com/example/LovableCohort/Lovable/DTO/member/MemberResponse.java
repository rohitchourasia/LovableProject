package com.example.LovableCohort.Lovable.DTO.member;

import com.example.LovableCohort.Lovable.Enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String email,
        String name,
        ProjectRole role,
        Instant invitedAt
) {
}
