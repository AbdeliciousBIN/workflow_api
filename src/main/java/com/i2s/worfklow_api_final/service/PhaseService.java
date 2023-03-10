package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Project;
import com.i2s.worfklow_api_final.repository.PhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PhaseService {
    private final PhaseRepository phaseRepository;

    @Autowired
    public PhaseService(PhaseRepository phaseRepository) {
        this.phaseRepository = phaseRepository;
    }

    public List<Phase> getAllPhases() {
        return phaseRepository.findAll();
    }

    public Optional<Phase> getPhaseById(Long id) {
        return phaseRepository.findById(id);
    }

    public Optional<Phase> getPhaseByPhaseNameAndProject(String phaseName, Project project) {
        return phaseRepository.findByPhaseNameAndProject(phaseName, project);
    }
    public Phase savePhase(Phase phase){
        return phaseRepository.save(phase);
    }
    public Phase updatePhase(Long id, Phase newPhase){
        Optional<Phase> optionalPhase = phaseRepository.findById(id);
        if(optionalPhase.isPresent()){
            Phase updatedPhase = optionalPhase.get();
            updatedPhase.setPhaseName(newPhase.getPhaseName());
            updatedPhase.setDescription(newPhase.getDescription());
            updatedPhase.setProject(newPhase.getProject());
            updatedPhase.setSteps(newPhase.getSteps());
            return phaseRepository.save(updatedPhase);
        }else{
            throw new EntityNotFoundException("Phase with ID" + id + "not found.");
        }
    }

    public void deletePhase(Long id ){
        Optional<Phase> phase = phaseRepository.findById(id);
        if(phase.isPresent()){
            phaseRepository.deleteById(phase.get().getId());
        }else{
            throw new EntityNotFoundException("Phase with ID "+ id + " not found." );
        }
    }
   /*public Optional<Phase> getPhaseByStepAndProjectIn(List<Step> steps, Project project) {
        return phaseRepository.findByStepsAndProjectIn(steps, project);
    }*/
}
