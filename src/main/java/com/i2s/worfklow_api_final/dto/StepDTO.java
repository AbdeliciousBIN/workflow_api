package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Step;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class StepDTO {
    private long id;
    private String stepName;
    private String description;
    @Getter(AccessLevel.NONE)
    private Phase phase;

    public StepDTO() {
    }

    public StepDTO(Step step) {
        this.id = step.getId();
        this.stepName = step.getStepName();
        this.description = step.getDescription();
        this.phase = step.getPhase();
    }


}
