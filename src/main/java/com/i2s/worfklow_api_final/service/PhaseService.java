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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhaseService {
    private final PhaseRepository phaseRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    @Autowired
    public PhaseService(PhaseRepository phaseRepository, ProjectRepository projectRepository, ProjectService projectService) {
        this.phaseRepository = phaseRepository;
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    public List<PhaseDTO> getAllPhases() {
        return phaseRepository.findAll().stream().map(PhaseDTO::new).collect(Collectors.toList());
    }

    public Optional<PhaseDTO> getPhaseById(long id) {
        return phaseRepository.findById(id).map(PhaseDTO::new);
    }

    public Optional<PhaseDTO> getPhaseByPhaseNameAndProject(String phaseName, ProjectDTO projectDTO) {
        return phaseRepository.findByPhaseNameAndProject(phaseName, new Project(projectDTO)).map(PhaseDTO::new);
    }
    public PhaseDTO savePhase(PhaseDTO phaseDTO){
        return new PhaseDTO(phaseRepository.save(new Phase(phaseDTO)));
    }
    public PhaseDTO updatePhase(long id, PhaseDTO newPhaseDTO){
        Optional<Phase> optionalPhase = phaseRepository.findById(id);
        if(optionalPhase.isPresent()){
            Phase updatedPhase = optionalPhase.get();
            updatedPhase.setPhaseName(newPhaseDTO.getPhaseName());
            updatedPhase.setDescription(newPhaseDTO.getDescription());
            updatedPhase.setProject(newPhaseDTO.getProject());
            updatedPhase.setSteps(newPhaseDTO.getSteps());
            return new PhaseDTO(phaseRepository.save(updatedPhase));
        }else{
            throw new EntityNotFoundException("Phase with ID" + id + "not found.");
        }
    }

    public void deletePhase(long id ){
        Optional<Phase> phase = phaseRepository.findById(id);
        if(phase.isPresent()){
            phaseRepository.deleteById(phase.get().getId());
            Project project = phase.get().getProject();
        }else{
            throw new EntityNotFoundException("Phase with ID "+ id + " not found." );
        }
    }
    public PhaseDTO createPhase(long projectId, PhaseDTO phaseDTO){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));
        Phase phase = new Phase(phaseDTO);
        phase.setProject(project);
        return new PhaseDTO(phaseRepository.save(phase));
    }
   /*public Optional<Phase> getPhaseByStepAndProjectIn(List<Step> steps, Project project) {
        return phaseRepository.findByStepsAndProjectIn(steps, project);
    }*/

    public long getPhaseIdByNameAndProjectName(String phaseName, String projectName){
        Optional<Project> optionalProject = projectService.getProjectByName(projectName).map(Project::new);
        if(optionalProject.isPresent()){
            Optional<Phase> optionalPhase = phaseRepository.findByPhaseNameAndProject(phaseName,optionalProject.get());
            if(optionalPhase.isPresent()){
                System.out.println("phase id in getPhaseId: "+ optionalPhase.get().getId());
                return optionalPhase.get().getId();
            }else{
                throw new EntityNotFoundException("Phase with Name" + phaseName + "not found.");
            }
        }else{
            throw new EntityNotFoundException("Project with Name" + projectName + "not found.");
        }
    }

    public List<PhaseDTO> getPhasesByProjectId(long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with ID " + id + " not found."));
        return phaseRepository.findByProject(project).stream().map(PhaseDTO::new).collect(Collectors.toList());
    }
}
