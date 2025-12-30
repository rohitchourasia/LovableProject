package com.example.LovableCohort.Lovable.DTO.auth;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record JwtUserPrincipal(Long userId, String email, List<GrantedAuthority> authorityList) {
}
