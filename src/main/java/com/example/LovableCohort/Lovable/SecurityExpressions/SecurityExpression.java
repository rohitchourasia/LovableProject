package com.example.LovableCohort.Lovable.SecurityExpressions;

import com.example.LovableCohort.Lovable.Enums.ProjectPermission;
import com.example.LovableCohort.Lovable.Jwt.JwtAuthImpl;
import com.example.LovableCohort.Lovable.Repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("security")
@RequiredArgsConstructor
public class SecurityExpression {
    private final ProjectMemberRepository projectMemberRepository;
    private final JwtAuthImpl jwtAuth ;
    private boolean hasAccess(Long projectId,ProjectPermission projectPermission){
        Long userId = jwtAuth.getUserId();
        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,userId).map(role->role.getProjectPermissionSet().contains(projectPermission)).orElse(false);
    }
    public boolean checkProjectAccessibilty(Long projectId){

            return  hasAccess(projectId,ProjectPermission.VIEW);
    }
    public boolean checkEditAccessibilty(Long projectId){
       return hasAccess(projectId,ProjectPermission.EDIT);
    }
    public boolean checkDeleteAccesibilty(Long projectId){
        return hasAccess(projectId,ProjectPermission.DELETE);
    }
    public boolean canManage(Long projectId){
        return hasAccess(projectId,ProjectPermission.MANAGE_MEMBERS);
    }
    public boolean canView(Long projectId){
        return hasAccess(projectId,ProjectPermission.VIEW_MEMBERS);
    }

}
