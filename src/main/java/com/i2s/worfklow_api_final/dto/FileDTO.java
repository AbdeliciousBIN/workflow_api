package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.File;
import com.i2s.worfklow_api_final.model.Task;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FileDTO {

    private long id;
    private String fileName;
    private String filePath;
    private long size;
    private String contentType;
    private LocalDateTime uploadDateTime;
    private List<Long> taskIds;

    public FileDTO() {
    }



}
