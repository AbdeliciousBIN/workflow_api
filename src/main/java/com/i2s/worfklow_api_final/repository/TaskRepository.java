package com.i2s.worfklow_api_final.repository;

import com.i2s.worfklow_api_final.enums.TaskStatus;
import com.i2s.worfklow_api_final.model.Step;
import com.i2s.worfklow_api_final.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAll();

    Optional<Task> findById(long id);

    List<Task> findByStep(Step step);
    @Query("SELECT t FROM Task t WHERE t.step.phase.project.id = :projectId")
    List<Task> findByProjectId(@Param("projectId") long projectId);

    List<Task> findByStatus(TaskStatus taskStatus);

    @Query("SELECT count(t) FROM Task t ")
    long countAllTasks();

    @Query("SELECT count(t) FROM Task t WHERE t.status = :status")
    long countAllTasksByStatus(@Param("status") TaskStatus status);

    @Query("SELECT count(t) FROM Task t WHERE t.step.phase.project.id = :projectId")
    long countByProjectId(@Param("projectId") long projectId);


    @Query("SELECT count(t) FROM Task t WHERE t.step.phase.project.id = :projectId AND t.status = :status")
    long countByProjectIdAndStatus(@Param("projectId") long projectId, @Param("status") TaskStatus status);
    @Query("SELECT count(t) FROM Task t WHERE t.step.phase.id = :phaseId AND t.step.phase.project.id = :projectId")
    long countByProjectIdAndPhaseId(@Param("projectId") long projectId, @Param("phaseId") long phaseId);

    @Query("SELECT count(t) FROM Task t WHERE t.step.phase.id = :phaseId AND t.step.phase.project.id = :projectId AND t.status = :status")
    long countByProjectIdAndPhaseIdAndStatus(@Param("projectId") long projectId, @Param("phaseId") long phaseId, @Param("status") TaskStatus status);

    @Query("SELECT count(t) FROM Task t WHERE t.step.id = :stepId AND t.step.phase.id = :phaseId AND t.step.phase.project.id = :projectId")
    long countByProjectIdAndPhaseIdAndStepId(@Param("projectId") long projectId, @Param("phaseId") long phaseId, @Param("stepId") long stepId);

    @Query("SELECT count(t) FROM Task t WHERE t.step.id = :stepId AND t.step.phase.id = :phaseId AND t.step.phase.project.id = :projectId AND t.status = :status")
    long countByProjectIdAndPhaseIdAndStepIdAndStatus(@Param("projectId") long projectId, @Param("phaseId") long phaseId, @Param("stepId") long stepId, @Param("status") TaskStatus status);

    // Get start and end times for all tasks
    @Query("SELECT t.startedAt, t.finishedAt FROM Task t WHERE t.status = :status")
    List<Object[]> getStartAndEndTimesForAllTasks(@Param("status") TaskStatus status);

    // Get start and end times for tasks in a specific project
    @Query("SELECT t.startedAt, t.finishedAt FROM Task t WHERE t.step.phase.project.id = :projectId AND t.status = :status")
    List<Object[]> getStartAndEndTimesForProject(@Param("projectId") long projectId, @Param("status") TaskStatus status);

    // Get start and end times for tasks in a specific phase of a project
    @Query("SELECT t.startedAt, t.finishedAt FROM Task t WHERE t.step.phase.project.id = :projectId AND t.step.phase.id = :phaseId AND t.status = :status")
    List<Object[]> getStartAndEndTimesForProjectAndPhase(@Param("projectId") long projectId, @Param("phaseId") long phaseId, @Param("status") TaskStatus status);

    // Get start and end times for tasks in a specific step of a phase of a project
    @Query("SELECT t.startedAt, t.finishedAt FROM Task t WHERE t.step.phase.project.id = :projectId AND t.step.phase.id = :phaseId AND t.step.id = :stepId AND t.status = :status")
    List<Object[]> getStartAndEndTimesForProjectAndPhaseAndStep(@Param("projectId") long projectId, @Param("phaseId") long phaseId, @Param("stepId") long stepId, @Param("status") TaskStatus status);




}
