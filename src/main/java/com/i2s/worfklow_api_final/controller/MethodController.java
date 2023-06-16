package com.i2s.worfklow_api_final.controller;

import com.i2s.worfklow_api_final.dto.MethodDTO;
import com.i2s.worfklow_api_final.service.ActionService;
import com.i2s.worfklow_api_final.service.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MethodController {

    private final MethodService methodService;


    @Autowired
    public MethodController(MethodService methodService, ActionService actionService){
        this.methodService = methodService;

    }

    @GetMapping("/user/methods")
    public ResponseEntity<List<MethodDTO>> getAllMethods(){

        return ResponseEntity.ok(methodService.getAllMethods());
    }

    @GetMapping("/user/methods/{id}")
    public ResponseEntity<MethodDTO> getMethodById(@PathVariable long id){
        return methodService.getMethodById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/methods")
    public ResponseEntity<?> createMethod(@RequestBody MethodDTO methodDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(methodService.createMethod(methodDTO));
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

    @DeleteMapping("/admin/methods/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable long id) {
        try {
            methodService.deleteMethod(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }
}
