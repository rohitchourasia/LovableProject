package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.project.ProjectRequest;
import com.example.LovableCohort.Lovable.DTO.project.ProjectResponse;
import com.example.LovableCohort.Lovable.DTO.project.ProjectSummaryResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectService {

     List<ProjectSummaryResponse> getMyProjects();

     ProjectResponse getUserProjectById(Long id);

     ProjectResponse createProject(ProjectRequest request);

     ProjectResponse updateProject(Long id, ProjectRequest request);

    void softDelete(Long id);
}
