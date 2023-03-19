package com.i2s.worfklow_api_final.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.i2s.worfklow_api_final.dto.PhaseDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "phases")
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "phase_name")
    private String phaseName;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Project project;

    @JsonIgnore
    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    private List<Step> steps;

    public Phase() {

    }
    public Phase(PhaseDTO phaseDTO){
        this.id = phaseDTO.getId();
        this.phaseName=phaseDTO.getPhaseName();
        this.description=phaseDTO.getDescription();
        this.project = phaseDTO.getProject();
        this.steps = phaseDTO.getSteps();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phase phase = (Phase) o;

        return getId() == phase.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Phase [id=" + id + ", phaseName=" + phaseName + ", description=" + description + "]";
    }

}
