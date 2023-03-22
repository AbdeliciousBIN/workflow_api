package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Project;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectDTO {
    private long id;
    private String projectName;
    private String description;
    private List<PhaseDTO> phases;
    public ProjectDTO() {
    }

    public List<PhaseDTO> getPhases() {
        return phases;
    }

    public void setPhases(List<PhaseDTO> phases) {

        this.phases = phases;
    }

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.projectName = project.getProjectName();
        this.description = project.getDescription();
        this.phases = project.getPhases().stream().map(PhaseDTO::new).collect(Collectors.toList());
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
