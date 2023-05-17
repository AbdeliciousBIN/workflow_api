package com.i2s.worfklow_api_final.service;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
@Service
public class StorageService {

    @Value("${filestorage.base-dir:uploads/}")
    private String fileStorageLocation;

    private Path storagePath;

    @PostConstruct
    public void init() {
        storagePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        try {
            FileUtils.forceMkdir(storagePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Could not create the directory for file storage.", e);
        }
    }

    public Path storeFile(MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        Path filePath = storagePath.resolve(originalFileName);

        try {
            multipartFile.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + originalFileName + ". Please try again.", e);
        }

        return filePath;
    }

    public void deleteFile(String filePath) {
        try {
            FileUtils.forceDelete(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file " + filePath + ". Please try again later.", e);
        }
    }
}
