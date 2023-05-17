package com.i2s.worfklow_api_final.controller;

import com.i2s.worfklow_api_final.dto.MethodExecutionDTO;
import com.i2s.worfklow_api_final.dto.TaskDTO;
import com.i2s.worfklow_api_final.dto.UserParameterDTO;
import com.i2s.worfklow_api_final.enums.TaskStatus;
import com.i2s.worfklow_api_final.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable long id) {
        return taskService.getTaskById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskDTO>> getTasksByJobIdsStatusProjectPhaseStep(@RequestParam("jobIds") List<Long> jobIds, @RequestParam("status") TaskStatus status, @RequestParam(value = "projectId", required = false) Long projectId, @RequestParam(value = "phaseId", required = false) Long phaseId, @RequestParam(value = "stepId", required = false) Long stepId) {

        return ResponseEntity.ok(taskService.getTasksByJobIdsStatusProjectPhaseStep(jobIds, status, projectId, phaseId, stepId));
    }


    @GetMapping("/{taskId}/methodExecutions")
    public ResponseEntity<List<MethodExecutionDTO>> getMethodExecutionsByTaskId(@PathVariable("taskId") long taskId) {
        List<MethodExecutionDTO> methodExecutionDTOs = taskService.getMethodExecutionsByTaskId(taskId);
        return ResponseEntity.ok(methodExecutionDTOs);
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<String> getTaskNameById(@PathVariable long id) {
        return taskService.getTaskNameById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/validation/verifiableByJob/{jobId}")
    public ResponseEntity<List<TaskDTO>> getTasksWaitingForValidationByJobIdAndProjectPhaseStep(@PathVariable long jobId, @RequestParam(required = false) Long projectId, @RequestParam(required = false) Long phaseId, @RequestParam(required = false) Long stepId) {
        try {
            List<TaskDTO> taskDTOs = taskService.getTasksWaitingForValidationByJobIdAndProjectPhaseStep(jobId, projectId, phaseId, stepId);
            return ResponseEntity.ok(taskDTOs);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskDTO));
        } catch (DataIntegrityViolationException e) {
            // handle database constraint violation error
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            // handle invalid input data error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        } catch (Exception e) {
            e.printStackTrace();

            // handle any other unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            // handle database constraint violation error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        } catch (IllegalArgumentException e) {
            // handle invalid input data error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        } catch (Exception e) {
            // handle any other unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @GetMapping("/step/{stepId}")
    public ResponseEntity<?> getTasksByStep(@PathVariable long stepId) {
        try {
            List<TaskDTO> tasks = taskService.getTasksByStep(stepId);
            return ResponseEntity.ok(tasks);
        } catch (EntityNotFoundException e) {
            // handle step not found error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Step with ID " + stepId + " not found.");
        } catch (IllegalArgumentException e) {
            // handle invalid input data error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        } catch (Exception e) {
            // handle any other unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @PostMapping("/{stepId}/startInitialTasks")
    public ResponseEntity<List<TaskDTO>> startInitialTasks(@PathVariable long stepId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.startInitialTasks(stepId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(@PathVariable long id, @RequestBody TaskStatus newStatus) {
        try {
            TaskDTO updatedTaskDTO = taskService.updateTaskStatus(id, newStatus);
            return ResponseEntity.status(HttpStatus.OK).body(updatedTaskDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{taskId}/executeMethods")
    public ResponseEntity<?> executeMethodsForTask(@PathVariable long taskId, @RequestBody Map<Long, List<UserParameterDTO>> userParametersByMethodExecutionId) {
        try {
            taskService.executeMethodsForTask(taskId, userParametersByMethodExecutionId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            // handle task not found error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found with ID: " + taskId);
        } catch (DataIntegrityViolationException e) {
            // handle database constraint violation error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        } catch (IllegalArgumentException e) {
            // handle invalid input data error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");

        } catch (Exception e) {
            // handle any other unexpected error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @PostMapping("/{taskId}/validateAndStartChildTasks")
    public ResponseEntity<?> validateAndStartChildTasks(@PathVariable long taskId) {
        try {
            taskService.validateAndStartChildTasks(taskId);
            return ResponseEntity.status(HttpStatus.OK).body("Task validation and child task starting successful.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with ID " + taskId + " not found.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @PostMapping("{taskId}/invalidate")
    public ResponseEntity<?> invalidateTask(@PathVariable long taskId) {
        try {
            taskService.invalidateTask(taskId);
            return ResponseEntity.status(HttpStatus.OK).body("Task invalidation successful.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with ID " + taskId + " not found.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }
}
