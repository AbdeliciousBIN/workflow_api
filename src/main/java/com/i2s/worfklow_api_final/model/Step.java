package com.i2s.worfklow_api_final.model;


import com.i2s.worfklow_api_final.dto.StepDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "steps")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "step_name")
    private String stepName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id")
    private Phase phase;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

    public Step() {

    }

    public Step(StepDTO stepDTO) {
        this.id = stepDTO.getId();
        this.stepName = stepDTO.getStepName();
        this.description = stepDTO.getDescription();
        //this.phase = stepDTO.getPhase();
    }


}
