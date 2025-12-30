package com.example.LovableCohort.Lovable.mapper;

import com.example.LovableCohort.Lovable.DTO.auth.UserProfileResponse;
import com.example.LovableCohort.Lovable.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {
   UserProfileResponse userToUserProfileResponse(User user);

}
