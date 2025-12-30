package com.example.LovableCohort.Lovable.Repository;

import com.example.LovableCohort.Lovable.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("""
            SELECT p FROM Project p
            WHERE p.deletedAt IS NULL
            AND EXISTS(Select pm from ProjectMember pm
            where pm.id.userId=:userId and p.id=pm.id.projectId)
            ORDER BY p.updatedAt DESC
            """
    )
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId);
    @Query("""
            SELECT p FROM Project p
            WHERE p.id = :id
                AND p.deletedAt IS NULL
                AND EXISTS (
                    SELECT 1 FROM ProjectMember pm
                    WHERE pm.id.userId = :userId
                    AND pm.id.projectId = :id
                )
            """)
    Optional<Project>findProjectyByUserandId(@Param("userId")Long userId ,@Param("id")Long id);


}
