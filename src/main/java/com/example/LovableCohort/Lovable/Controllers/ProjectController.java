package com.example.LovableCohort.Lovable.Controllers;

import com.example.LovableCohort.Lovable.DTO.project.ProjectRequest;
import com.example.LovableCohort.Lovable.DTO.project.ProjectResponse;
import com.example.LovableCohort.Lovable.DTO.project.ProjectSummaryResponse;
import com.example.LovableCohort.Lovable.Services.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "api/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponse>> getMyProjects() {

        return ResponseEntity.ok(projectService.getMyProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {

        return ResponseEntity.ok(projectService.getUserProjectById(id));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody @Valid  ProjectRequest request) {

        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {

        projectService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

}
