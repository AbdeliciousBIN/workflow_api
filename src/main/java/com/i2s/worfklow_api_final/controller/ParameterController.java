package com.i2s.worfklow_api_final.controller;

import com.i2s.worfklow_api_final.dto.ParameterDTO;
import com.i2s.worfklow_api_final.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ParameterController {

    private final ParameterService parameterService;

    @Autowired
    public ParameterController(ParameterService parameterService){
        this.parameterService = parameterService;
    }

    @GetMapping("/user/parameters")
    public ResponseEntity<List<ParameterDTO>> getAllParameters(){
        return ResponseEntity.ok(parameterService.getAllParameters());
    }

    @GetMapping("/user/parameters/{id}")
    public ResponseEntity<ParameterDTO> getParameterById(@PathVariable long id){
        return parameterService.getParameterById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/parameters")
    public ResponseEntity<?> createParameter(@RequestBody ParameterDTO parameterDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(parameterService.createParameter(parameterDTO));
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

    @DeleteMapping("/admin/parameters/{id}")
    public ResponseEntity<?> deleteParameter(@PathVariable long id) {
        try {
            parameterService.deleteParameter(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            // handle database constraint violation error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        } catch (EntityNotFoundException e) {
            // handle entity not found error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parameter with ID " + id + " not found.");
        } catch (Exception e) {
            // handle any other unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

}





