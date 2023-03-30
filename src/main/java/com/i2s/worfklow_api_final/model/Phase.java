package com.i2s.worfklow_api_final.model;

import com.i2s.worfklow_api_final.dto.PhaseDTO;
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
@Table(name = "phases")
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "phase_name")
    private String phaseName;


    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Step> steps;

    public Phase() {

    }

    public Phase(PhaseDTO phaseDTO) {
        if (phaseDTO.getId() != 0) this.id = phaseDTO.getId();
        this.phaseName = phaseDTO.getPhaseName();
        this.description = phaseDTO.getDescription();

    }


}
