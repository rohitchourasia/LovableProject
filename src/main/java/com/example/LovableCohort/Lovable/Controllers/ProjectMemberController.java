package com.example.LovableCohort.Lovable.Controllers;

import com.example.LovableCohort.Lovable.DTO.member.InviteMemberRequest;
import com.example.LovableCohort.Lovable.DTO.member.MemberResponse;
import com.example.LovableCohort.Lovable.Entity.ProjectMember;
import com.example.LovableCohort.Lovable.Services.ProjectMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getProjectMembers(@PathVariable Long projectId) {

        return ResponseEntity.ok(projectMemberService.getProjectMembers(projectId));
    }

    @PostMapping
    public ResponseEntity<MemberResponse> inviteMember(
            @PathVariable Long projectId,
            @RequestBody @Valid  InviteMemberRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                projectMemberService.inviteMember(projectId, request)
        );
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(
            @PathVariable Long projectId,
            @PathVariable Long memberId,
            @RequestBody @Valid  InviteMemberRequest request
    ) {

        return ResponseEntity.ok(projectMemberService.updateMemberRole(projectId, memberId, request));
    }
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long projectId,
            @PathVariable Long memberId
    ) {

        projectMemberService.deleteProjectMember(projectId, memberId);
        return ResponseEntity.noContent().build();
    }
}
