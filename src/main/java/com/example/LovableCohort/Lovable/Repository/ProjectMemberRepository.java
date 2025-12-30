package com.example.LovableCohort.Lovable.Repository;

import com.example.LovableCohort.Lovable.Entity.ProjectMember;
import com.example.LovableCohort.Lovable.Entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByIdProjectId(Long projectId) ;
}
