package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Project;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProjectDTO {
    private long id;
    private String projectName;
    private String description;

    private List<PhaseDTO> phases;

    public ProjectDTO() {
    }


    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.projectName = project.getProjectName();
        this.description = project.getDescription();
        if (project.getPhases() != null)
            this.phases = project.getPhases().stream().map(PhaseDTO::new).collect(Collectors.toList());
    }


}
