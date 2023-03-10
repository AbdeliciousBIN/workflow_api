package com.i2s.worfklow_api_final.controller;


import com.i2s.worfklow_api_final.dto.ProjectDTO;
import com.i2s.worfklow_api_final.model.Project;
import com.i2s.worfklow_api_final.service.ProjectService;
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
@RequestMapping("/projects")
public class ProjectController
{
    private ProjectService projectService;
    @Autowired
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects(){
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDTO> projectsDTO = projects.stream().map(ProjectDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(projectsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id){
        Optional<Project> project = projectService.getProjectById(id);
        if(project.isPresent()) return ResponseEntity.ok(new ProjectDTO(project.get()));
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO){
        try {
            Project createdProject = projectService.saveProject(new Project(projectDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(new ProjectDTO(createdProject));
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
    public ResponseEntity<?> updateProject(@PathVariable Long id,@RequestBody ProjectDTO projectDTO){
        try{
            Project updatedProject = projectService.updateProject(id,new Project(projectDTO));
            return ResponseEntity.ok(new ProjectDTO(updatedProject));
        } catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation error occurred.");
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data error occurred.");
        }catch(OptimisticLockingFailureException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Project has been modified by another user. Please refresh and try again.");
        }catch(Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
        try{
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch(EntityNotFoundException e){
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
