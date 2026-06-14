package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.DTO.project.ProjectRequest;
import com.example.LovableCohort.Lovable.DTO.project.ProjectResponse;
import com.example.LovableCohort.Lovable.DTO.project.ProjectSummaryResponse;
import com.example.LovableCohort.Lovable.Entity.Project;
import com.example.LovableCohort.Lovable.Entity.ProjectMember;
import com.example.LovableCohort.Lovable.Entity.ProjectMemberId;
import com.example.LovableCohort.Lovable.Entity.User;
import com.example.LovableCohort.Lovable.Enums.ProjectRole;
import com.example.LovableCohort.Lovable.Jwt.JwtAuthImpl;
import com.example.LovableCohort.Lovable.Repository.ProjectMemberRepository;
import com.example.LovableCohort.Lovable.Repository.ProjectRepository;
import com.example.LovableCohort.Lovable.Repository.UserRepository;
import com.example.LovableCohort.Lovable.Services.ProjectService;
import com.example.LovableCohort.Lovable.Services.ProjectTemplateService;
import com.example.LovableCohort.Lovable.errors.ResourceNotFoundException;
import com.example.LovableCohort.Lovable.mapper.ProjectMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@FieldDefaults(makeFinal = true,level= AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {
    ProjectRepository projectRepository ;
    UserRepository userRepository ;
    ProjectMapper projectMapper ;
    ProjectMemberRepository projectMemberRepository;
    ProjectTemplateService projectTemplateService;
    JwtAuthImpl jwtAuth ;

    @Override
    public List<ProjectSummaryResponse> getMyProjects() {
        Long userId = jwtAuth.getUserId();
        List<Project>projects = projectRepository.findAllAccessibleByUser(userId);
        Stream<Project>projectStream=projects.stream();
        List<ProjectSummaryResponse>pr = projectStream.map(projectMapper::projectToProjectSummaryResponse)
                .toList() ;


        return pr ;
    }
    @PreAuthorize("@security.checkProjectAccessibilty(#id)")
    @Override
    public ProjectResponse getUserProjectById(Long id) {
        Long userId = jwtAuth.getUserId();
        Project project = getAllAssociatedProject(id,userId);
        return projectMapper.projectToProjectResponse(project);

    }

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        //User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User",userId.toString()));
        Long reqId = jwtAuth.getUserId();
        User user = userRepository.getReferenceById(reqId);
        Project project = Project.builder()
                .name(request.name())
                .build() ;
       Project proj =  projectRepository.save(project);
        ProjectMemberId projectMemberId= new ProjectMemberId(proj.getId(),user.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .projectRole(ProjectRole.OWNER)
                .project(proj)
                .user(user)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .build();
        projectMemberRepository.save(projectMember);
        projectTemplateService.initializeProjectFromTemplate(proj.getId());
       return projectMapper.projectToProjectResponse(proj);

    }

    @Override
    @PreAuthorize("@security.checkEditAccessibilty(#id)")
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Long userId = jwtAuth.getUserId();
        Project project = getAllAssociatedProject(id,userId);
        project.setName(request.name());
       Project p =  projectRepository.save(project);
        return projectMapper.projectToProjectResponse(p);

    }

    @Override
    @PreAuthorize("@security.checkDeleteAccesibilty(#id)")
    public void softDelete(Long id) {
        Long userId = jwtAuth.getUserId();
            Project deleteProject = getAllAssociatedProject(id,userId);
            deleteProject.setDeletedAt(Instant.now());


    }
    public Project getAllAssociatedProject(Long id , Long userId){
        return projectRepository.findProjectyByUserandId(userId,id).orElseThrow(()->new ResourceNotFoundException("Project",id.toString()));
    }
}
