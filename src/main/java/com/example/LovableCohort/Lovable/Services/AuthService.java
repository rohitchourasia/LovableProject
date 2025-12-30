package com.example.LovableCohort.Lovable.Services;


import com.example.LovableCohort.Lovable.DTO.auth.AuthRequest;
import com.example.LovableCohort.Lovable.DTO.auth.LoginRequest;
import com.example.LovableCohort.Lovable.DTO.auth.SignUpRequest;

public interface AuthService {

    AuthRequest login(LoginRequest loginRequest);

    AuthRequest signup(SignUpRequest signUpRequest);
}
