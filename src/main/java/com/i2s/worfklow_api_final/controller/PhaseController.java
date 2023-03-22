package com.i2s.worfklow_api_final.controller;


import com.i2s.worfklow_api_final.dto.PhaseDTO;
import com.i2s.worfklow_api_final.service.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phases")
public class PhaseController {
    private final PhaseService phaseService;

    @Autowired
    public PhaseController(PhaseService phaseService) {
        this.phaseService = phaseService;
    }

    @GetMapping
    public ResponseEntity<List<PhaseDTO>> getAllPhases() {
        return ResponseEntity.ok(phaseService.getAllPhases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhaseDTO> getPhaseById(@PathVariable long id) {
        Optional<PhaseDTO> phaseDTO = phaseService.getPhaseById(id);
        if (phaseDTO.isPresent()) return ResponseEntity.ok(phaseDTO.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/idByNameAndProjectName")
    public ResponseEntity<?> getPhaseIdByNameAndProjectName(@RequestParam("phaseName") String phaseName, @RequestParam("projectName") String projectName) {
        try {

            return ResponseEntity.ok(phaseService.getPhaseIdByNameAndProjectName(phaseName, projectName));
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

    @GetMapping("project/{id}")
    public ResponseEntity<?> getPhasesByProjectId(@PathVariable long id) {
        try {
            return ResponseEntity.ok(phaseService.getPhasesByProjectId(id));
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


    @PostMapping("/projects/{projectId}")
    public ResponseEntity<?> createPhase(@PathVariable Long projectId, @RequestBody PhaseDTO phaseDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(phaseService.createPhase(projectId, phaseDTO));
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

    @PutMapping("/phases/{id}")
    public ResponseEntity<?> updatePhase(@PathVariable Long id, @RequestBody PhaseDTO phaseDTO) {
        try {
            return ResponseEntity.ok(phaseService.updatePhase(id, phaseDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        } catch (OptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phase has been modified by another user. Please refresh and try again.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }

    }

    @DeleteMapping("/phases/{id}")
    public ResponseEntity<?> deletePhase(@PathVariable Long id) {
        try {
            phaseService.deletePhase(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }
}
