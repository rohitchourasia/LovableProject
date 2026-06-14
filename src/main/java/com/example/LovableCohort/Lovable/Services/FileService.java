package com.example.LovableCohort.Lovable.Services;

import com.example.LovableCohort.Lovable.DTO.project.FileContentResponse;
import com.example.LovableCohort.Lovable.DTO.project.FileNode;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface FileService {
     FileNode getFileTree(Long userId, Long projectId);

     FileContentResponse getFileContent(Long projectId, String path);
     void saveFile(Long projectId, String filePath, String fileContent);
     List<FileNode> getFileTree(Long projectId);
}
