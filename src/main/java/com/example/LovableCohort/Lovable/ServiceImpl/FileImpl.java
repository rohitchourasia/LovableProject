package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.DTO.project.FileContentResponse;
import com.example.LovableCohort.Lovable.DTO.project.FileNode;
import com.example.LovableCohort.Lovable.Services.FileService;
import org.springframework.stereotype.Service;

@Service
public class FileImpl implements FileService {
    @Override
    public FileNode getFileTree(Long userId, Long projectId) {
        return null;
    }

    @Override
    public FileContentResponse getFileContent(Long projectId, Long userId, String path) {
        return null;
    }
}
