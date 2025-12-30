package com.example.LovableCohort.Lovable.Controllers;

import com.example.LovableCohort.Lovable.DTO.auth.AuthRequest;
import com.example.LovableCohort.Lovable.DTO.auth.LoginRequest;
import com.example.LovableCohort.Lovable.DTO.auth.SignUpRequest;
import com.example.LovableCohort.Lovable.DTO.auth.UserProfileResponse;
import com.example.LovableCohort.Lovable.Services.AuthService;
import com.example.LovableCohort.Lovable.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService ;
    private final UserService userService ;

    @PostMapping("/login")
    public ResponseEntity<AuthRequest>login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        AuthRequest authRequest= authService.login(loginRequest);
        Cookie cookie = new Cookie("token",authRequest.token());
        response.addCookie(cookie);
        return ResponseEntity.ok(authRequest);

    }
    @PostMapping("/signup")
    public ResponseEntity<AuthRequest>signup(@RequestBody SignUpRequest signup){
        return ResponseEntity.ok(authService.signup(signup));
    }
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse>getme(){
        Long userId =1L;
        return ResponseEntity.ok(userService.getUser(userId));
    }

}
