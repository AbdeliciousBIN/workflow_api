package com.i2s.worfklow_api_final.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.i2s.worfklow_api_final.dto.StepDTO;

import javax.persistence.*;

@Entity
@Table(name = "steps")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "step_name")
    private String stepName;

    @Column(name = "description")
    private String description;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id")
    private Phase phase;

    public Step() {

    }

    public Step(StepDTO stepDTO){
        this.id = stepDTO.getId();
        this.stepName = stepDTO.getStepName();
        this.description = stepDTO.getDescription();
        this.phase = stepDTO.getPhase();
    }
    public long getId() {
        return id;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Step step = (Step) o;

        return getId() == step.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Step [id=" + id + ", stepName=" + stepName + ", description=" + description + ", phase=" + phase + "]";
    }
}
