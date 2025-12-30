package com.example.LovableCohort.Lovable.Jwt;

import com.example.LovableCohort.Lovable.DTO.auth.JwtUserPrincipal;
import com.example.LovableCohort.Lovable.DTO.auth.SignUpRequest;
import com.example.LovableCohort.Lovable.Entity.User;
import com.example.LovableCohort.Lovable.Security.CustomizedUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level= AccessLevel.PRIVATE)
public class JwtAuthImpl {
    CustomizedUserDetailService customizedUserDetailService;
    String key = "9f3a1c8e7d4b2a6f0e9c5d7b8a4f1c2e6b9d0a7c3e5f4b1a8d6c2e9f7a4b5";
    SecretKey secretKey(){
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }
    public String createToken(User user){
        String token = Jwts.builder()
                .subject(user.getEmail())
                .claim("userId",user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*20))
                .signWith(secretKey())
                .compact() ;

        return token ;
    }
   public JwtUserPrincipal getUserEmailIdfromToken(String token){

       Claims claims = Jwts.parser()
               .verifyWith(secretKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();

        String email = claims.getSubject();
        Long userId = claims.get("userId",Long.class);
       return new JwtUserPrincipal( userId,email,new ArrayList<>());

    }
   public Long getUserId(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if(authentication==null || !(authentication.getPrincipal() instanceof JwtUserPrincipal)){
           System.out.println("the authentication"+authentication);

           throw new AuthenticationCredentialsNotFoundException("User not authenticated");
       }
       JwtUserPrincipal loggedinUser = (JwtUserPrincipal) authentication.getPrincipal();
       return loggedinUser.userId();
   }
}
