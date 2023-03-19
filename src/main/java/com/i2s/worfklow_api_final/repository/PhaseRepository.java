package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {
    List<Phase> findAll();

    Optional<Phase> findById(Long id);

    Optional<Phase> findByPhaseNameAndProject(String phaseName, Project project);

    List<Phase> findByProject(Project project);


    //Optional<Phase> findByStepsAndProject(List<Step> steps, Project project);
    // Optional<Phase> findByStepsAndProjectIn(List<Step> steps, Project project);



}