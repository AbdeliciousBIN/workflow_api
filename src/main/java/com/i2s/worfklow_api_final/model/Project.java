package com.i2s.worfklow_api_final.model;

import com.i2s.worfklow_api_final.dto.ProjectDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "project_name")
    private String projectName;


    private String description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Phase> phases;

    public Project(String projectName, String description, List<Phase> phases) {
        this.projectName = projectName;
        this.description = description;
        this.phases = phases;
    }

    public Project() {
    }

    public Project(ProjectDTO projectDTO) {
        if (projectDTO.getId() == 0) this.id = projectDTO.getId();
        this.projectName = projectDTO.getProjectName();
        this.description = projectDTO.getDescription();
        if (projectDTO.getPhases() != null)
            this.phases = projectDTO.getPhases().stream().map(Phase::new).collect(Collectors.toList());

    }


}
