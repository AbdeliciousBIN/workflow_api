package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Job;
import com.i2s.worfklow_api_final.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAll();

    Optional<Job> findById(long id);
}
