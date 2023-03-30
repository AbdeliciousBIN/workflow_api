package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Phase;
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
public class PhaseDTO {
    private long id;
    private String phaseName;
    private String description;
    private List<StepDTO> steps;


    public PhaseDTO() {
    }

    public PhaseDTO(Phase phase) {
        this.id = phase.getId();
        this.phaseName = phase.getPhaseName();
        this.description = phase.getDescription();
        if (phase.getSteps() != null)
            this.steps = phase.getSteps().stream().map(StepDTO::new).collect(Collectors.toList());


    }


}
