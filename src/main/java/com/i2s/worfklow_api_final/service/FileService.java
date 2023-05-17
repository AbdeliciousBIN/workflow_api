package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.dto.FileDTO;
import com.i2s.worfklow_api_final.model.File;
import com.i2s.worfklow_api_final.model.Task;
import com.i2s.worfklow_api_final.repository.FileRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class FileService {
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;
    private final StorageService storageService;

    @Autowired
    public FileService(FileRepository fileRepository, ModelMapper modelMapper, StorageService storageService) {
        this.fileRepository = fileRepository;
        this.modelMapper = modelMapper;

        this.storageService = storageService;
    }

    public FileDTO storeFile(@Valid MultipartFile multipartFile) {
        Path filePath = storageService.storeFile(multipartFile);

        String fileName = filePath.getFileName().toString();
        String contentType = multipartFile.getContentType();
        long fileSize = multipartFile.getSize();

        File file = new File();
        file.setFileName(fileName);
        file.setFilePath(filePath.toString());
        file.setSize(fileSize);
        file.setContentType(contentType);
        file.setUploadDateTime(LocalDateTime.now());

        File savedFile = fileRepository.save(file);
        return modelMapper.map(savedFile, FileDTO.class);
    }

    public Optional<FileDTO> getFileById(long id) {
        return fileRepository.findById(id).map(file -> {
            FileDTO fileDTO = modelMapper.map(file, FileDTO.class);
            fileDTO.setTaskIds(file.getTasks().stream().map(Task::getId).collect(Collectors.toList()));
            return fileDTO;
        });
    }

    public List<FileDTO> getAllFiles() {
        return fileRepository.findAll().stream().map(file -> {
            FileDTO fileDTO = modelMapper.map(file, FileDTO.class);
            fileDTO.setTaskIds(file.getTasks().stream().map(Task::getId).collect(Collectors.toList()));
            return fileDTO;
        }).collect(Collectors.toList());
    }

    public void deleteFile(long id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("File with ID " + id + " not found."));

        storageService.deleteFile(file.getFilePath());

        fileRepository.deleteById(id);
    }

    public Optional<Resource> loadFileAsResource(long id) {
        Optional<File> fileOptional = fileRepository.findById(id);

        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            try {
                Path filePath = Paths.get(file.getFilePath()).normalize();
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists()) {
                    return Optional.of(resource);
                }
            } catch (MalformedURLException ex) {
                System.out.println(ex);

            }
        }

        return Optional.empty();
    }
}
