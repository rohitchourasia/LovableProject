package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.DTO.auth.AuthRequest;
import com.example.LovableCohort.Lovable.DTO.auth.LoginRequest;
import com.example.LovableCohort.Lovable.DTO.auth.SignUpRequest;
import com.example.LovableCohort.Lovable.DTO.auth.UserProfileResponse;
import com.example.LovableCohort.Lovable.Entity.User;
import com.example.LovableCohort.Lovable.Jwt.JwtAuthImpl;
import com.example.LovableCohort.Lovable.Repository.UserRepository;
import com.example.LovableCohort.Lovable.Services.AuthService;
import com.example.LovableCohort.Lovable.errors.BadRequestException;
import com.example.LovableCohort.Lovable.mapper.AuthenticationMapper;
import com.example.LovableCohort.Lovable.mapper.ProjectMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class AuthImpl implements AuthService {
    PasswordEncoder passwordEncoder;
    UserRepository repo ;
    JwtAuthImpl jwtAuthImpl ;
    ProjectMapper projectMapper ;
    AuthenticationMapper mapper ;
    AuthenticationManager authenticationManager ;
    @Override
    public AuthRequest login(LoginRequest req) {

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(),req.password()));
        User authenticatedUser = (User)auth.getPrincipal();
        String jwtToken = jwtAuthImpl.createToken(authenticatedUser);
        UserProfileResponse userProfileResponse= mapper.userToUserProfileResponse(authenticatedUser);

        return new AuthRequest(jwtToken,userProfileResponse) ;
    }

    @Override
    public AuthRequest signup(SignUpRequest signUpRequest) {
        repo.findByEmail(signUpRequest.email()).ifPresent((user)->{ throw new BadRequestException("User with this mail id already present");
        });
        User newUser = User.builder()
                .name(signUpRequest.name())
                .passwordHash(passwordEncoder.encode(signUpRequest.password()))
                .email(signUpRequest.email())
                .build();
        newUser = repo.save(newUser);
        String jwtToken = jwtAuthImpl.createToken(newUser);
        UserProfileResponse userProfileResponse= mapper.userToUserProfileResponse(newUser);
        AuthRequest authRequest = AuthRequest.builder()
                .token(jwtToken)
                .user(userProfileResponse)
                .build() ;

        return authRequest;
    }
}
