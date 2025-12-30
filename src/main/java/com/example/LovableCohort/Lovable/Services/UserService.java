package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.auth.UserProfileResponse;

public interface UserService {
     UserProfileResponse getUser(Long userId);
}
