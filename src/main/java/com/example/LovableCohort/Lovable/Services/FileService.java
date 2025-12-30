package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.project.FileContentResponse;
import com.example.LovableCohort.Lovable.DTO.project.FileNode;
import org.jspecify.annotations.Nullable;

public interface FileService {
     FileNode getFileTree(Long userId, Long projectId);

     FileContentResponse getFileContent(Long projectId, Long userId, String path);
}
