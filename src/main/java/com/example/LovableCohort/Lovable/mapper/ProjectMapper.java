package com.example.LovableCohort.Lovable.mapper;

import com.example.LovableCohort.Lovable.DTO.auth.UserProfileResponse;
import com.example.LovableCohort.Lovable.DTO.project.ProjectResponse;
import com.example.LovableCohort.Lovable.DTO.project.ProjectSummaryResponse;
import com.example.LovableCohort.Lovable.Entity.Project;
import com.example.LovableCohort.Lovable.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse projectToProjectResponse(Project project);
    ProjectSummaryResponse projectToProjectSummaryResponse(Project project);

    //UserProfileResponse userToUserProfileResponse(User user);
}
