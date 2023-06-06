package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.enums.TaskStatus;
import com.i2s.worfklow_api_final.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticsService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final PhaseRepository phaseRepository;
    private final StepRepository stepRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;


    @Autowired
    public StatisticsService(TaskRepository taskRepository, ProjectRepository projectRepository, PhaseRepository phaseRepository, StepRepository stepRepository, UserRepository userRepository, JobRepository jobRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.phaseRepository = phaseRepository;
        this.stepRepository = stepRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }


    public double getCompletionRateForAllTasks() {
        long totalTasks = taskRepository.countAllTasks();
        long completedTasks = taskRepository.countAllTasksByStatus(TaskStatus.FINISHED);

        return totalTasks > 0 ? (double) completedTasks / totalTasks : 0.0;
    }

    public double getCompletionRateForProject(long projectId) {
        long totalTasks = taskRepository.countByProjectId(projectId);
        long completedTasks = taskRepository.countByProjectIdAndStatus(projectId, TaskStatus.FINISHED);

        return totalTasks > 0 ? (double) completedTasks / totalTasks : 0.0;
    }

    public double getCompletionRateForProjectAndPhase(long projectId, long phaseId) {
        long totalTasks = taskRepository.countByProjectIdAndPhaseId(projectId, phaseId);
        long completedTasks = taskRepository.countByProjectIdAndPhaseIdAndStatus(projectId, phaseId, TaskStatus.FINISHED);

        return totalTasks > 0 ? (double) completedTasks / totalTasks : 0.0;
    }

    public double getCompletionRateForProjectAndPhaseAndStep(long projectId, long phaseId, long stepId) {
        long totalTasks = taskRepository.countByProjectIdAndPhaseIdAndStepId(projectId, phaseId, stepId);
        long completedTasks = taskRepository.countByProjectIdAndPhaseIdAndStepIdAndStatus(projectId, phaseId, stepId, TaskStatus.FINISHED);

        return totalTasks > 0 ? (double) completedTasks / totalTasks : 0.0;
    }

    public long getCountForAllTasks() {
        return taskRepository.countAllTasks();
    }

    public long getCountForAllTasksByStatus(TaskStatus status) {
        return taskRepository.countAllTasksByStatus(status);
    }

    public long getCountForProjectByStatus(long projectId, TaskStatus status) {
        return taskRepository.countByProjectIdAndStatus(projectId, status);
    }

    public long getCountForProjectAndPhaseByStatus(long projectId, long phaseId, TaskStatus status) {
        return taskRepository.countByProjectIdAndPhaseIdAndStatus(projectId, phaseId, status);
    }

    public long getCountForProjectPhaseAndStepByStatus(long projectId, long phaseId, long stepId, TaskStatus status) {
        return taskRepository.countByProjectIdAndPhaseIdAndStepIdAndStatus(projectId, phaseId, stepId, status);
    }

    public double calculateAverageTime(List<Object[]> startAndEndTimes) {
        if (startAndEndTimes.isEmpty()) {
            return 0.0;
        }
        double totalTimeInSeconds = 0.0;
        for (Object[] times : startAndEndTimes) {
            LocalDateTime start = (LocalDateTime) times[0];
            LocalDateTime end = (LocalDateTime) times[1];
            if (start != null && end != null) {
                Duration duration = Duration.between(start, end);
                totalTimeInSeconds += duration.getSeconds();
            }
        }
        return totalTimeInSeconds / startAndEndTimes.size();
    }

    public double getAverageCompletionTimeForAllTasks() {
        List<Object[]> startAndEndTimes = taskRepository.getStartAndEndTimesForAllTasks(TaskStatus.FINISHED);
        return calculateAverageTime(startAndEndTimes);
    }

    public double getAverageCompletionTimeForProject(long projectId) {
        List<Object[]> startAndEndTimes = taskRepository.getStartAndEndTimesForProject(projectId, TaskStatus.FINISHED);
        return calculateAverageTime(startAndEndTimes);
    }

    public double getAverageCompletionTimeForProjectAndPhase(long projectId, long phaseId) {
        List<Object[]> startAndEndTimes = taskRepository.getStartAndEndTimesForProjectAndPhase(projectId, phaseId, TaskStatus.FINISHED);
        return calculateAverageTime(startAndEndTimes);
    }

    public double getAverageCompletionTimeForProjectAndPhaseAndStep(long projectId, long phaseId, long stepId) {
        List<Object[]> startAndEndTimes = taskRepository.getStartAndEndTimesForProjectAndPhaseAndStep(projectId, phaseId, stepId, TaskStatus.FINISHED);
        return calculateAverageTime(startAndEndTimes);
    }

    public long getCountForAllProjects() {
        return projectRepository.countProjects();
    }

    public long getCountForAllPhases() {
        return phaseRepository.countPhases();
    }

    public long getCountForAllSteps() {
        return stepRepository.countSteps();
    }

    public long getCountForPhasesInProject(long projectId) {
        return phaseRepository.countPhasesInProject(projectId);
    }

    public long getCountForStepsInPhase(long phaseId) {
        return stepRepository.countStepsInPhase(phaseId);
    }
    public long getCountForStepsInProject(long projectId){
        return stepRepository.countStepsInProject(projectId);
    }

    public long getCountForAllUsers() {
        return userRepository.countUsers();
    }

    public long getCountForAllJobs() {
        return jobRepository.countJobs();
    }
}
