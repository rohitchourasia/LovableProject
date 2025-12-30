package com.example.LovableCohort.Lovable.errors;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?>handleResourceNotFound(ResourceNotFoundException ex){
        ApiError error = new ApiError(HttpStatus.NOT_FOUND,ex.getResourceName()+"was not found and the projectId is"+ex.getResourceId(), LocalDateTime.now(),null);
        return ResponseEntity.status(error.status()).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?>handleValiddationError(MethodArgumentNotValidException ex){
        List<ApiErrorField> errofields=ex.getBindingResult().getFieldErrors().stream().map(f->new ApiErrorField(f.getField(),f.getDefaultMessage())).toList();
        ApiError error= new ApiError(HttpStatus.BAD_REQUEST,"Input Validation Failed",LocalDateTime.now(),errofields);
        return ResponseEntity.status(error.status()).body(error);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now(),null);

        return ResponseEntity.status(apiError.status()).body(apiError);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError>handleAutheticationException(AuthenticationException ex){
        ApiError apiError= new ApiError(HttpStatus.UNAUTHORIZED,ex.getMessage(),LocalDateTime.now(),null);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError>handleJwtException(JwtException ex){
        ApiError apiError= new ApiError(HttpStatus.UNAUTHORIZED,ex.getMessage(),LocalDateTime.now(),null);
        return ResponseEntity.status(apiError.status()).body(apiError);

    }

}
