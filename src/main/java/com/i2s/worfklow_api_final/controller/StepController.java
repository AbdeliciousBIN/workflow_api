package com.i2s.worfklow_api_final.controller;

import com.i2s.worfklow_api_final.dto.StepDTO;
import com.i2s.worfklow_api_final.model.Step;
import com.i2s.worfklow_api_final.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/steps")
public class StepController {
    private StepService stepService;

    @Autowired
    public StepController(StepService stepService){
        this.stepService = stepService;
    }

    @GetMapping
    public ResponseEntity<List<StepDTO>> getAllSteps(){
        List<Step> steps = stepService.getAllSteps();
        List<StepDTO> stepsDTO = steps.stream().map(StepDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(stepsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepDTO> getStepById(@PathVariable Long id ){
        Optional<Step> step = stepService.getStepById(id);
        if(step.isPresent()) return ResponseEntity.ok(new StepDTO(step.get()));
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createStep(@RequestBody StepDTO stepDTO){
        try{
            Step step = stepService.saveStep(new Step(stepDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(new StepDTO(step));
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
    public ResponseEntity<?> updateStep(@PathVariable Long id, @RequestBody StepDTO stepDTO){
        try{
            Step step = stepService.updateStep(id,new Step(stepDTO));
            return ResponseEntity.ok(new StepDTO(step));
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        }catch(OptimisticLockingFailureException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Step has been modified by another user. Please refresh and try again.");
        }catch(Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStep(@PathVariable Long id ){
        try{
            stepService.deleteStep(id);
            return ResponseEntity.noContent().build() ;
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
