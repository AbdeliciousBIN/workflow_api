package com.i2s.worfklow_api_final.model;

import com.i2s.worfklow_api_final.dto.ProjectDTO;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "project_name")
    private String projectName;

    @Column(name = "description")
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
        if(projectDTO.getId() == 0) this.id = projectDTO.getId();
        this.projectName = projectDTO.getProjectName();
        this.description = projectDTO.getDescription();
        if( projectDTO.getPhases() !=null) this.phases = projectDTO.getPhases().stream().map(Phase::new).collect(Collectors.toList());

    }

    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return getId() == project.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", projectName=" + projectName + ", description=" + description + "]";
    }
}
