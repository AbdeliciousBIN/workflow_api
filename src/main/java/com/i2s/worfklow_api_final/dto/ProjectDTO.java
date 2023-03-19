package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Project;

import java.util.List;

public class ProjectDTO {
    private long id;
    private String projectName;
    private String description;
    private List<Phase> phases;
    public ProjectDTO() {
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.projectName = project.getProjectName();
        this.description = project.getDescription();
        this.phases = project.getPhases();
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
    public String toString() {
        return "ProjectDTO [id=" + id + ", projectName=" + projectName + ", description=" + description + "]";
    }

}
