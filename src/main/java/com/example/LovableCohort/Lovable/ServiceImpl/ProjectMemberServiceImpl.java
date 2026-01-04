package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.DTO.member.InviteMemberRequest;
import com.example.LovableCohort.Lovable.DTO.member.MemberResponse;
import com.example.LovableCohort.Lovable.Entity.Project;
import com.example.LovableCohort.Lovable.Entity.ProjectMember;
import com.example.LovableCohort.Lovable.Entity.ProjectMemberId;
import com.example.LovableCohort.Lovable.Entity.User;
import com.example.LovableCohort.Lovable.Jwt.JwtAuthImpl;
import com.example.LovableCohort.Lovable.Repository.ProjectMemberRepository;
import com.example.LovableCohort.Lovable.Repository.ProjectRepository;
import com.example.LovableCohort.Lovable.Repository.UserRepository;
import com.example.LovableCohort.Lovable.Services.ProjectMemberService;
import com.example.LovableCohort.Lovable.errors.BadRequestException;
import com.example.LovableCohort.Lovable.errors.ResourceNotFoundException;
import com.example.LovableCohort.Lovable.mapper.ProjectMemberResponseMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Service
@FieldDefaults(makeFinal = true,level= AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {
    ProjectRepository projectRepository ;
    ProjectMemberResponseMapper projectMemberResponseMapper;
    ProjectMemberRepository projectMemberRepository ;
    UserRepository userRepository;
    JwtAuthImpl jwtAuth ;
    @Override
    @PreAuthorize("@security.canView(#projectId)")
    public List<MemberResponse> getProjectMembers(Long projectId) {

        List<MemberResponse>members = new ArrayList<>() ;

        members.addAll(projectMemberRepository.findByIdProjectId(projectId).stream().map(projectMemberResponseMapper::toMemberResponsefromProjectMember).toList()) ;
        return members;


    }

    @Override
    @PreAuthorize("@security.canManage(#projectId)")
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request) {
        Long userId = jwtAuth.getUserId();
        Project p = projectRepository.findProjectyByUserandId(userId,projectId).orElseThrow() ;

        User user = userRepository.findByEmail(request.email()).orElseThrow(()->new RuntimeException("User not in the database")) ;
        ProjectMemberId projectMemberId= new ProjectMemberId(p.getId(),user.getId()) ;
        if(projectMemberRepository.existsById(projectMemberId)){
            System.out.println("find duplicate projectmemberId");
            throw new BadRequestException("User is already added");
        }
        ProjectMember projectMember = ProjectMember.builder()
                .project(p)
                .user(user)
                .projectRole(request.role())
                .id(projectMemberId)
                .invitedAt(Instant.now())
                .build();
        ProjectMember saveprojectMember = projectMemberRepository.save(projectMember);



        return projectMemberResponseMapper.toMemberResponsefromProjectMember(saveprojectMember);
    }

    @Override
    @PreAuthorize("@security.canManage(#projectId)")
    public MemberResponse updateMemberRole(Long projectId, Long memberId, InviteMemberRequest request) {
        Long userId = jwtAuth.getUserId();
        Project p = projectRepository.findById(projectId).orElseThrow(()->new ResourceNotFoundException("pROJECT",projectId.toString()));

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,memberId);
        ProjectMember memberUpdate = projectMemberRepository.findById(projectMemberId).orElseThrow(()->new ResourceNotFoundException("ProjectMember",projectMemberId.toString()));
        memberUpdate.setProjectRole(request.role());
        projectMemberRepository.save(memberUpdate);

        return projectMemberResponseMapper.toMemberResponsefromProjectMember(memberUpdate);
    }

    @Override
    @PreAuthorize("@security.canManage(#projectId)")
    public  void deleteProjectMember(Long projectId, Long memberId) {
        Long userId = jwtAuth.getUserId();
        Project p = projectRepository.findProjectyByUserandId(userId,projectId).orElseThrow(()->new ResourceNotFoundException("project member",projectId.toString())) ;


        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,memberId);
        if(!projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Member record not found");
        }
        ProjectMember deleteMember = projectMemberRepository.findById(projectMemberId).orElseThrow();
        projectMemberRepository.delete(deleteMember);

    }

    public Project getAllAssociatedProject(Long id , Long userId){
        return projectRepository.findProjectyByUserandId(id,userId).orElseThrow();
    }
}
