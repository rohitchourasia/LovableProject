package com.example.LovableCohort.Lovable.Controllers;

import com.example.LovableCohort.Lovable.DTO.project.FileContentResponse;
import com.example.LovableCohort.Lovable.DTO.project.FileNode;
import com.example.LovableCohort.Lovable.Services.FileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/projects/{id}/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService  ;
    @GetMapping
    public ResponseEntity<FileNode>getFileNode(@PathVariable Long projectId){
        Long userId = 1L ;
        return ResponseEntity.ok(fileService.getFileTree(userId,projectId));
    }
    @GetMapping(path="/{projectId}/{*path}")
    public ResponseEntity<FileContentResponse>getResponse(@PathVariable Long projectId,@PathVariable  String path){
        Long userId= 1L ;
        return ResponseEntity.ok(fileService.getFileContent(projectId,path));
    }


}
