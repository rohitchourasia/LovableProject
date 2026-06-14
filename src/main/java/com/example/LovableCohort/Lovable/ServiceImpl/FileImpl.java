package com.example.LovableCohort.Lovable.ServiceImpl;

import com.example.LovableCohort.Lovable.DTO.project.FileContentResponse;
import com.example.LovableCohort.Lovable.DTO.project.FileNode;
import com.example.LovableCohort.Lovable.Entity.Project;
import com.example.LovableCohort.Lovable.Entity.ProjectFile;
import com.example.LovableCohort.Lovable.Repository.ProjectFileRepository;
import com.example.LovableCohort.Lovable.Repository.ProjectRepository;
import com.example.LovableCohort.Lovable.Services.FileService;
import com.example.LovableCohort.Lovable.errors.ResourceNotFoundException;
import com.example.LovableCohort.Lovable.mapper.ProjectFiletoFileNodeMapper;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class FileImpl implements FileService {
    @Value("${minio.project-bucket}")
    private final String projectBucket;
    private final ProjectRepository projectRepository ;
    private final MinioClient minioClient ;
    private final ProjectFiletoFileNodeMapper projectFiletoFileNodeMapper ;
    private ProjectFileRepository projectFileRepository ;
    private static final String BUCKET_NAME = "projects";

    @Override
    public FileNode getFileTree(Long userId, Long projectId) {
        return null;
    }

    @Override
    public FileContentResponse getFileContent(Long projectId,  String path) {
        String objectName = projectId + "/" + path;
        try (
                InputStream is = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(BUCKET_NAME)
                                .object(objectName)
                                .build())) {

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return new FileContentResponse(path, content);
        } catch (Exception e) {
            log.error("Failed to read file: {}/{}", projectId, path, e);
            throw new RuntimeException("Failed to read file content", e);
        }
    }


    @Override
    public void saveFile(Long projectId, String path, String content) {
        // STEP 1: Validate project existence before allocating storage resources
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));

// STEP 2: Normalize the file path by stripping any leading slashes
        String cleanPath = path.startsWith("/") ? path.substring(1) : path;

// STEP 3: Construct a unique namespace object key for multi-tenant isolation in MinIO
        String objectKey = projectId + "/" + cleanPath;

        log.info("Clean Path: {}, Object Key: {}", cleanPath, objectKey);

        try {
            // STEP 4: Convert raw string payload to bytes using standard character encoding
            byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

            // STEP 5: Stream binary data into an InputStream for memory-efficient I/O transfer
            InputStream inputStream = new ByteArrayInputStream(contentBytes);

            // STEP 6: Transport binary object to remote MinIO server bucket via HTTP SDK client
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(projectBucket)
                            .object(objectKey)
                            .stream(inputStream, contentBytes.length, -1) // Uses default multipart configuration
                            .contentType(determineContentType(path))
                            .build()
            );

            // STEP 7: Sync persistence metadata layer in PostgreSQL; Content remains isolated in S3/MinIO
            ProjectFile file = projectFileRepository.findByProjectIdAndPath(projectId, cleanPath)
                    .orElseGet(() -> ProjectFile.builder()
                            .project(project)
                            .path(cleanPath)
                            .minioObjectKey(objectKey)
                            .createdAt(Instant.now())
                            .build());

            // Update entity modification tracking metadata
            file.setUpdatedAt(Instant.now());
            projectFileRepository.save(file);

            log.info("Successfully saved file and updated metadata context: {}", objectKey);

        } catch (Exception e) {
            // Catch-all block handles network dropouts, minio storage faults, or database deadlocks
            log.error("Failed execution during file save transaction lifecycle for {}/{}", projectId, cleanPath, e);
            throw new RuntimeException("File save transaction failed", e);
        }

        }

    private String determineContentType(String path) {
        String type = URLConnection.guessContentTypeFromName(path);
        if (type != null) return type;
        if (path.endsWith(".jsx") ||path.endsWith(".ts") || path.endsWith(".tsx")) return "text/javascript";
        if (path.endsWith(".json")) return "application/json";
        if (path.endsWith(".css")) return "text/css";

        return "text/plain";
    }
    @Override
    public List<FileNode> getFileTree(Long projectId){
        List<FileNode>projectFiles =  projectFileRepository.findByProjectId(projectId)
                .stream()
                .map(projectFile -> projectFiletoFileNodeMapper.projectFileResponsetoFileNoderespnse(projectFile)).toList();
        return projectFiles;

    }

}
