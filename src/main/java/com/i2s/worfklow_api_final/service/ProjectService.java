package com.i2s.worfklow_api_final.service;


import com.i2s.worfklow_api_final.dto.PhaseDTO;
import com.i2s.worfklow_api_final.dto.ProjectDTO;
import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Project;
import com.i2s.worfklow_api_final.repository.PhaseRepository;
import com.i2s.worfklow_api_final.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final PhaseRepository phaseRepository;


    @Autowired
    public ProjectService(ProjectRepository projectRepository, PhaseRepository phaseRepository) {
        this.projectRepository = projectRepository;
        this.phaseRepository = phaseRepository;

    }

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream().map(ProjectDTO::new).collect(Collectors.toList());

    }

    public Optional<ProjectDTO> getProjectById(long id) {

        return projectRepository.findById(id).map(ProjectDTO::new);
    }

    public Optional<ProjectDTO> getProjectByName(String projectName) {
        return projectRepository.findByProjectName(projectName).map(ProjectDTO::new);
    }

    public Optional<ProjectDTO> getProjectByPhases(@Valid List<PhaseDTO> phasesDTO) {
        return projectRepository.findByPhasesIn(phasesDTO.stream().map(Phase::new).collect(Collectors.toList())).map(ProjectDTO::new);
    }

    public ProjectDTO saveProject(@Valid ProjectDTO projectDTO) {

        return new ProjectDTO(projectRepository.save(new Project(projectDTO)));
    }

    public void deleteProjectById(long id) {
        projectRepository.deleteById(id);
    }

    public ProjectDTO updateProject(long id, @Valid ProjectDTO newProjectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with ID " + id + " not found."));
        project.setProjectName(newProjectDTO.getProjectName());
        project.setDescription(newProjectDTO.getDescription());
        Project updatedProject = projectRepository.save(project);
       return new ProjectDTO(updatedProject);
    }

    public void deleteProject(long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with ID " + id + " not found."));
        projectRepository.deleteById(id);
    }

    public long getProjectIdByName(String projectName) {
        Optional<ProjectDTO> optionalProjectDTO = this.getProjectByName(projectName);
        if (optionalProjectDTO.isPresent()) {
            return optionalProjectDTO.get().getId();
        } else {
            throw new EntityNotFoundException("Project with Name" + projectName + "not found.");
        }
    }
}
