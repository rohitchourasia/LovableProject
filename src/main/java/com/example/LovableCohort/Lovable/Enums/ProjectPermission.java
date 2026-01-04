package com.example.LovableCohort.Lovable.Enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProjectPermission {
    VIEW("project:view"),
    EDIT(("project:edit")),
    DELETE(("project:delete")),
    MANAGE_MEMBERS(("project:manage_member")),
    VIEW_MEMBERS(("project:view_members"));
    private final String value;
}
