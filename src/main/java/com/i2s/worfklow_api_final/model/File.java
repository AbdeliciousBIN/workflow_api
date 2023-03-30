package com.i2s.worfklow_api_final.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "file_name")
    private String fileName;

    @Column(nullable = false, name = "file_path")
    private String filePath;
    private long size;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "upload_date_time")
    private LocalDateTime uploadDateTime;

    @ManyToMany(mappedBy = "files")
    private List<Task> tasks = new ArrayList<>();

    // constructors

    public File() {
        this.uploadDateTime = LocalDateTime.now();
    }


}
