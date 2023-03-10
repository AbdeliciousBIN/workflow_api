package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Step;
import com.i2s.worfklow_api_final.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class StepService {
    private final StepRepository stepRepository;

    @Autowired
    public StepService(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    public List<Step> getAllSteps() {
        return stepRepository.findAll();
    }

    public Optional<Step> getStepById(Long id) {
        return stepRepository.findById(id);
    }

    public List<Step> getStepByPhase(Phase phase) {
        return stepRepository.findByPhase(phase);
    }

    public Step saveStep(Step step){
        return stepRepository.save(step);
    }

    public Step updateStep(Long id, Step newStep){
        Optional<Step> step = stepRepository.findById(id);
        if(step.isPresent()){
            Step updatedStep = step.get();
            updatedStep.setStepName(newStep.getStepName());
            updatedStep.setDescription(newStep.getDescription());
            updatedStep.setPhase(newStep.getPhase());
            return stepRepository.save(updatedStep);
        }else{
            throw new EntityNotFoundException("Step with ID" + id + "not found.");
        }
    }

    public void deleteStep(Long id ){
        Optional<Step> step = stepRepository.findById(id);
        if(step.isPresent()){
            stepRepository.deleteById(step.get().getId());
        }else{
            throw new EntityNotFoundException("Step with ID" + id + "not found.");
        }
    }
}
