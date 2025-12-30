package com.example.LovableCohort.Lovable.errors;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        HttpStatus status,
        String message ,
        LocalDateTime createdAt,
        List<ApiErrorField> apiErrorFieldList
) {
}
record ApiErrorField(
        String message ,
        String field
){

}
