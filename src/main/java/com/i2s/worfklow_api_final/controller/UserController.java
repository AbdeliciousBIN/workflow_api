package com.i2s.worfklow_api_final.controller;

import com.i2s.worfklow_api_final.dto.JobDTO;
import com.i2s.worfklow_api_final.dto.UserDTO;
import com.i2s.worfklow_api_final.model.Job;
import com.i2s.worfklow_api_final.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/users/{id}/job")
    public ResponseEntity<JobDTO> getJobByUserId(@PathVariable long id) {
        return userService.getJobByUserId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/admin/users")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
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

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
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


    @GetMapping("/user/users/name/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name) {
        try {
            UserDTO userDTO = userService.getUserByFullName(name);
            return ResponseEntity.ok(userDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // handle any other unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }
}
