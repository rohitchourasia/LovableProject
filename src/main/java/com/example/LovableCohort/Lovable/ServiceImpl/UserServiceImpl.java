package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.DTO.auth.UserProfileResponse;
import com.example.LovableCohort.Lovable.Services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserProfileResponse getUser(Long userId) {
        return null;
    }
}
