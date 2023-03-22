package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAll();

    Optional<Project> findById(long id);

    Optional<Project> findByProjectName(String projectName);


    Optional<Project> findByPhasesIn(List<Phase> phase);


}
