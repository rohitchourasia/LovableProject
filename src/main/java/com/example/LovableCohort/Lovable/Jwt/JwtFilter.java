package com.example.LovableCohort.Lovable.Jwt;

import com.example.LovableCohort.Lovable.DTO.auth.JwtUserPrincipal;
import com.example.LovableCohort.Lovable.Entity.User;
import com.example.LovableCohort.Lovable.Security.CustomizedUserDetailService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
@RequiredArgsConstructor

public class JwtFilter extends OncePerRequestFilter {
   private final CustomizedUserDetailService customizedUserDetailService;
  private  final  JwtAuthImpl jwtAuthImpl ;
    @Autowired
    @Qualifier("handlerExceptionResolver")
   private  HandlerExceptionResolver handlerExceptionResolver ;
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        System.out.println(request.getServletPath());
        return request.getServletPath().startsWith("/api/auth");

    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("entering here");
        try {
            if (request.getHeader("Authorization") == null) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = request.getHeader("Authorization");
            if (!token.startsWith("Bearer") || SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }
            token = token.split("Bearer ")[1];
            System.out.println(token);
            JwtUserPrincipal jwtUserPrincipal = jwtAuthImpl.getUserEmailIdfromToken(token);
            System.out.println("verifcation done succesfully");
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwtUserPrincipal, null, jwtUserPrincipal.authorityList());
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (JwtException ex) {
            System.out.println(ex.getMessage());
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }

    }
}
