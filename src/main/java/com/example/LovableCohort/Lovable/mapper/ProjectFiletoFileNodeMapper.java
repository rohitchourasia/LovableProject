package com.example.LovableCohort.Lovable.mapper;

import com.example.LovableCohort.Lovable.DTO.project.FileNode;
import com.example.LovableCohort.Lovable.Entity.ProjectFile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectFiletoFileNodeMapper {
    FileNode projectFileResponsetoFileNoderespnse(ProjectFile projectFile);
}
