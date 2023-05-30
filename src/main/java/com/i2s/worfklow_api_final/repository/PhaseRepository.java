package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {
    List<Phase> findAll();

    Optional<Phase> findById(long id);

    Optional<Phase> findByPhaseNameAndProject(String phaseName, Project project);

    List<Phase> findByProject(Project project);

    @Query("SELECT count(ph) FROM Phase ph")
    long countPhases();
    @Query("SELECT count(ph) FROM Phase ph WHERE ph.project.id = :projectId")
    long countPhasesInProject(@Param("projectId") long projectId);
}