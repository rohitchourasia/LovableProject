package com.example.LovableCohort.Lovable.Repository;

import com.example.LovableCohort.Lovable.Entity.ProjectMember;
import com.example.LovableCohort.Lovable.Entity.ProjectMemberId;
import com.example.LovableCohort.Lovable.Enums.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByIdProjectId(Long projectId) ;

    @Query("""
            select p.projectRole from ProjectMember p where p.id.userId=:userId and
            p.id.projectId=:projectId
            """)
    Optional<ProjectRole> findRoleByProjectIdAndUserId(@Param("projectId")Long projectId, @Param("userId") Long userId);
}
