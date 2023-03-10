package com.i2s.worfklow_api_final.model;


import com.i2s.worfklow_api_final.dto.ProjectDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "project_name")
    private String projectName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Phase> phases;

    public Project(String projectName, String description, List<Phase> phases) {
        this.projectName = projectName;
        this.description = description;
        this.phases = phases;
    }

    public Project() {
    }

    public Project(ProjectDTO projectDTO){
        this.id=projectDTO.getId();
        this.projectName=projectDTO.getProjectName();
        this.description = projectDTO.getDescription();
        this.phases = projectDTO.getPhases();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        return getId().equals(project.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", projectName=" + projectName + ", description=" + description + "]";
    }
}
