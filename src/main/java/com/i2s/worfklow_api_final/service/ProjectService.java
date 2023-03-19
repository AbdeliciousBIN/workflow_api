package com.i2s.worfklow_api_final.service;


import com.i2s.worfklow_api_final.dto.PhaseDTO;
import com.i2s.worfklow_api_final.dto.ProjectDTO;
import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Project;
import com.i2s.worfklow_api_final.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectDTO> getAllProjects() {

        return projectRepository.findAll().stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    public Optional<ProjectDTO> getProjectById(Long id) {

        return projectRepository.findById(id).map(ProjectDTO::new);
    }

    public Optional<ProjectDTO> getProjectByName(String projectName) {
        return projectRepository.findByProjectName(projectName).map(ProjectDTO::new);
    }

    public Optional<ProjectDTO> getProjectByPhases(List<PhaseDTO> phasesDTO) {
        return projectRepository.findByPhasesIn(phasesDTO.stream().map(Phase::new).collect(Collectors.toList())).map(ProjectDTO::new);
    }

    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        return new ProjectDTO(projectRepository.save( new Project(projectDTO)));
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }

    public ProjectDTO updateProject(Long id, ProjectDTO newProjectDTO){
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()){
            Project project = optionalProject.get();
            project.setProjectName(newProjectDTO.getProjectName());
            project.setDescription(newProjectDTO.getDescription());
            return new ProjectDTO(projectRepository.save(project));
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

    public long getProjectIdByName(String projectName){
        Optional<ProjectDTO> optionalProjectDTO = this.getProjectByName(projectName);
        if(optionalProjectDTO.isPresent()){
            return optionalProjectDTO.get().getId();
        }else{
            throw new EntityNotFoundException("Project with Name" + projectName + "not found.");
        }
    }
}
