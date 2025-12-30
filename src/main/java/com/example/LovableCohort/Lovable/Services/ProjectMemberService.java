package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.member.InviteMemberRequest;
import com.example.LovableCohort.Lovable.DTO.member.MemberResponse;
import com.example.LovableCohort.Lovable.Entity.ProjectMember;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectMemberService {
     List<MemberResponse> getProjectMembers(Long projectId);

     MemberResponse inviteMember(Long projectId, InviteMemberRequest request);

     MemberResponse updateMemberRole(Long projectId, Long memberId, InviteMemberRequest request);

     void deleteProjectMember(Long projectId, Long memberId);
}
