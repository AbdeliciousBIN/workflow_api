package com.i2s.worfklow_api_final.controller;

import com.i2s.worfklow_api_final.dto.FileDTO;
import com.i2s.worfklow_api_final.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFileById(@PathVariable long id) {
        return fileService.getFileById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<FileDTO>> getFileByProject(@PathVariable long projectId) {
        return ResponseEntity.ok(fileService.getFilesByProject(projectId));
    }

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(fileService.storeFile(file));
        } catch (DataIntegrityViolationException e) {
            // handle database constraint violation error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        } catch (IllegalArgumentException e) {
            // handle invalid input data error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        } catch (Exception e) {
            // handle any other unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            // handle database constraint violation error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        } catch (IllegalArgumentException e) {
            // handle invalid input data error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        } catch (Exception e) {
            // handle any other unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable long id) {
        Optional<Resource> resourceOptional = fileService.loadFileAsResource(id);

        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();

            String contentType = fileService.getFileById(id)
                    .map(FileDTO::getContentType)
                    .orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            String fileName = resource.getFilename();
            String fileExtension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.')) : "";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .header("X-File-Extension", fileExtension)
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found with ID " + id);
        }
    }


}