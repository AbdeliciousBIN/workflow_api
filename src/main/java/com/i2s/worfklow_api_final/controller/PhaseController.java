package com.i2s.worfklow_api_final.controller;


import com.i2s.worfklow_api_final.dto.PhaseDTO;
import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.service.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/phases")
public class PhaseController {
    private PhaseService phaseService;

    @Autowired
    public PhaseController(PhaseService phaseService){
        this.phaseService=phaseService;
    }

    @GetMapping
    public ResponseEntity<List<PhaseDTO>> getAllPhases(){
        List<Phase> phases = phaseService.getAllPhases();
        List<PhaseDTO> phasesDTO = phases.stream().map(PhaseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(phasesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhaseDTO> getPhaseById(@PathVariable Long id){
        Optional<Phase> phase = phaseService.getPhaseById(id);
        if(phase.isPresent()) return ResponseEntity.ok(new PhaseDTO(phase.get()));
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createPhase(@RequestBody PhaseDTO phaseDTO){
    try{
       Phase createdPhase = phaseService.savePhase(new Phase(phaseDTO));
       return ResponseEntity.status(HttpStatus.CREATED).body(new PhaseDTO(createdPhase));
    }catch (DataIntegrityViolationException e) {
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePhase(@PathVariable Long id ,@RequestBody PhaseDTO phaseDTO){
    try{
        Phase updatedPhase = phaseService.updatePhase(id,new Phase(phaseDTO));
        return ResponseEntity.ok(new PhaseDTO(updatedPhase));
    }catch(EntityNotFoundException e){
    return ResponseEntity.notFound().build();
    }catch(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
    }catch(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
    }catch(OptimisticLockingFailureException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Phase has been modified by another user. Please refresh and try again.");
    }catch(Exception e ){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
    }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhase(@PathVariable Long id){
        try{
           phaseService.deletePhase(id);
           return ResponseEntity.noContent().build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        }catch(Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }
}
