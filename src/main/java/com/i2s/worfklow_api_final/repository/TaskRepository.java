package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.enums.TaskStatus;
import com.i2s.worfklow_api_final.model.Step;
import com.i2s.worfklow_api_final.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAll();

    Optional<Task> findById(long id);

    List<Task> findByStep(Step step);

    List<Task> findByStatus(TaskStatus taskStatus);


}
