package com.example.LovableCohort.Lovable.mapper;

import com.example.LovableCohort.Lovable.DTO.member.MemberResponse;
import com.example.LovableCohort.Lovable.Entity.ProjectMember;
import com.example.LovableCohort.Lovable.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberResponseMapper {
    @Mapping(target="userId",source = "id")
    @Mapping(target ="role",constant = "OWNER")
    MemberResponse toMemberResponsefromUser(User user);
    @Mapping(target="role",source="projectRole")
    @Mapping(target="name",source="user.name")
    @Mapping(target="userId",source="user.id")
    @Mapping(target="email", source="user.email")
    MemberResponse toMemberResponsefromProjectMember(ProjectMember member);
}
