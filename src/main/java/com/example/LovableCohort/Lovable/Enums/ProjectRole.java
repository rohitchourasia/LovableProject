package com.example.LovableCohort.Lovable.Enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum ProjectRole {
    VIEWER(
            Set.of(ProjectPermission.VIEW,ProjectPermission.VIEW_MEMBERS)

    ),

    EDITOR(
            Set.of(ProjectPermission.VIEW, ProjectPermission.EDIT,ProjectPermission.VIEW_MEMBERS)
    ),

    OWNER(
            Set.of(
                    ProjectPermission.VIEW,
                    ProjectPermission.EDIT,
                    ProjectPermission.DELETE,
                    ProjectPermission.MANAGE_MEMBERS,
                    ProjectPermission.VIEW_MEMBERS
            )
    );

    private final Set<ProjectPermission> projectPermissionSet;
}

