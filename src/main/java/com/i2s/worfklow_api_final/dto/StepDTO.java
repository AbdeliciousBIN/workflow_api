package com.i2s.worfklow_api_final.dto;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Step;

public class StepDTO {
    private long id;
    private String stepName;
    private String description;
    private Phase phase; // get not exposed

    public StepDTO() {
    }

    public StepDTO(Step step) {
        this.id = step.getId();
        this.stepName = step.getStepName();
        this.description = step.getDescription();
        this.phase = step.getPhase();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Phase getPhase() {
//        return phase;
//    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    @Override
    public String toString() {
        return "StepDTO [id=" + id + ", stepName=" + stepName + ", description=" + description + ", phase=" + phase + "]";
    }
}
