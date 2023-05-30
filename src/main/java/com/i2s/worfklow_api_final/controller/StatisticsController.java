package com.i2s.worfklow_api_final.controller;

import com.i2s.worfklow_api_final.enums.TaskStatus;
import com.i2s.worfklow_api_final.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/tasks/completionRate/all")
    public ResponseEntity<?> getCompletionRateForAllTasks() {
        try {
            double completionRate = statisticsService.getCompletionRateForAllTasks();
            return ResponseEntity.ok(completionRate);
        } catch (Exception e) {
            // handle any unexpected error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }


    @GetMapping("/tasks/completionRate/project/{projectId}")
    public ResponseEntity<?> getCompletionRateForProject(@PathVariable long projectId) {
        try {
            double completionRate = statisticsService.getCompletionRateForProject(projectId);
            return ResponseEntity.ok(completionRate);
        } catch (Exception e) {
            // handle any unexpected error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/completionRate/project/{projectId}/phase/{phaseId}")
    public ResponseEntity<?> getCompletionRateForProjectAndPhase(@PathVariable long projectId, @PathVariable long phaseId) {
        try {
            double completionRate = statisticsService.getCompletionRateForProjectAndPhase(projectId, phaseId);
            return ResponseEntity.ok(completionRate);
        } catch (Exception e) {
            // handle any unexpected error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/completionRate/project/{projectId}/phase/{phaseId}/step/{stepId}")
    public ResponseEntity<?> getCompletionRateForProjectPhaseAndStep(@PathVariable long projectId, @PathVariable long phaseId, @PathVariable long stepId) {
        try {
            double completionRate = statisticsService.getCompletionRateForProjectAndPhaseAndStep(projectId, phaseId, stepId);
            return ResponseEntity.ok(completionRate);
        } catch (Exception e) {
            // handle any unexpected error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/count/all")
    public ResponseEntity<?> getCountForAllTasks() {
        try {
            return ResponseEntity.ok(statisticsService.getCountForAllTasks());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/count/all/{status}")
    public ResponseEntity<?> getCountForAllTasksByStatus(@PathVariable TaskStatus status) {
        try {
            long completionRate = statisticsService.getCountForAllTasksByStatus(status);
            return ResponseEntity.ok(completionRate);
        } catch (Exception e) {
            // handle any unexpected error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/count/project/{projectId}/{status}")
    public ResponseEntity<?> getCountForProjectByStatus(@PathVariable long projectId, @PathVariable TaskStatus status) {
        try {
            return ResponseEntity.ok(statisticsService.getCountForProjectByStatus(projectId, status));
        } catch (Exception e) {
            // handle any unexpected error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/count/project/{projectId}/phase/{phaseId}/{status}")
    public ResponseEntity<?> getCountForProjectAndPhaseByStatus(@PathVariable long projectId, @PathVariable long phaseId, @PathVariable TaskStatus status) {
        try {
            return ResponseEntity.ok(statisticsService.getCountForProjectAndPhaseByStatus(projectId, phaseId, status));
        } catch (Exception e) {
            // handle any unexpected error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/count/project/{projectId}/phase/{phaseId}/step/{stepId}/{status}")
    public ResponseEntity<?> getCountForProjectPhaseAndStepByStatus(@PathVariable long projectId, @PathVariable long phaseId, @PathVariable long stepId, @PathVariable TaskStatus status) {
        try {
            return ResponseEntity.ok(statisticsService.getCountForProjectPhaseAndStepByStatus(projectId, phaseId, stepId, status));
        } catch (Exception e) {
            // handle any unexpected error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/averageCompletionTime/all")
    public ResponseEntity<?> getAverageCompletionTimeForAllTasks() {
        try {
            return ResponseEntity.ok(statisticsService.getAverageCompletionTimeForAllTasks());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/averageCompletionTime/project/{projectId}")
    public ResponseEntity<?> getAverageCompletionTimeForProject(@PathVariable long projectId) {
        try {
            return ResponseEntity.ok(statisticsService.getAverageCompletionTimeForProject(projectId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/averageCompletionTime/project/{projectId}/phase/{phaseId}")
    public ResponseEntity<?> getAverageCompletionTimeForProjectAndPhase(@PathVariable long projectId, @PathVariable long phaseId) {
        try {
            return ResponseEntity.ok(statisticsService.getAverageCompletionTimeForProjectAndPhase(projectId, phaseId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/tasks/averageCompletionTime/project/{projectId}/phase/{phaseId}/step/{stepId}")
    public ResponseEntity<?> getAverageCompletionTimeForProjectAndPhaseAndStep(@PathVariable long projectId, @PathVariable long phaseId, @PathVariable long stepId) {
        try {
            return ResponseEntity.ok(statisticsService.getAverageCompletionTimeForProjectAndPhaseAndStep(projectId, phaseId, stepId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/count/allProjects")
    public ResponseEntity<?> getCountForAllProjects() {
        try {
            return ResponseEntity.ok(statisticsService.getCountForAllProjects());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/count/allPhases")
    public ResponseEntity<?> getCountForAllPhases() {
        try {
            return ResponseEntity.ok(statisticsService.getCountForAllPhases());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/count/allSteps")
    public ResponseEntity<?> getCountForAllSteps() {
        try {
            return ResponseEntity.ok(statisticsService.getCountForAllSteps());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/count/phasesInProject/{projectId}")
    public ResponseEntity<?> getCountForPhasesInProject(@PathVariable long projectId) {
        try {
            return ResponseEntity.ok(statisticsService.getCountForPhasesInProject(projectId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/count/stepsInPhase/{phaseId}")
    public ResponseEntity<?> getCountForStepsInPhase(@PathVariable long phaseId) {
        try {
            return ResponseEntity.ok(statisticsService.getCountForStepsInPhase(phaseId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/count/allUsers")
    public ResponseEntity<?> getCountForAllUsers() {
        try {
            return ResponseEntity.ok(statisticsService.getCountForAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/count/allJobs")
    public ResponseEntity<?> getCountForAllJobs() {
        try {
            return ResponseEntity.ok(statisticsService.getCountForAllJobs());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }
}
