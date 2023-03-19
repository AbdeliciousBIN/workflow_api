package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findAll();

    Optional<Step> findById(Long id);
    List<Step> findByPhase(Phase phase);




}
