package com.i2s.worfklow_api_final.service;


import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Project;
import com.i2s.worfklow_api_final.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Optional<Project> getProjectByName(String projectName) {
        return projectRepository.findByProjectName(projectName);
    }

    public Optional<Project> getProjectByPhases(List<Phase> phases) {
        return projectRepository.findByPhasesIn(phases);
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }

    public Project updateProject(Long id, Project newProject){
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()){
            Project project = optionalProject.get();
            project.setProjectName(newProject.getProjectName());
            project.setDescription(project.getDescription());
            return projectRepository.save(project);
        }else{
            // project not found
            throw new EntityNotFoundException("Project with ID" + id + "not found.");
        }
    }

    public void deleteProject(Long id){
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()){
            projectRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Project with ID "+ id + " not found." );
        }
    }
}
