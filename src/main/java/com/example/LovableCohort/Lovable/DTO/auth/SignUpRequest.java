package com.example.LovableCohort.Lovable.DTO.auth;

public record SignUpRequest(
        String email,
        String name,
        String password
) {
}
