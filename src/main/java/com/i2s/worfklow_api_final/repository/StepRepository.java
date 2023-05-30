package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findAll();

    Optional<Step> findById(long id);

    List<Step> findByPhase(Phase phase);
    @Query("SELECT count(s) FROM Step s")
    long countSteps();

    @Query("SELECT count(s) FROM Step s WHERE s.phase.id = :phaseId")
    long countStepsInPhase(@Param("phaseId") long phaseId);
}
