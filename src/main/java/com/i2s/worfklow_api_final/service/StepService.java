package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.dto.StepDTO;
import com.i2s.worfklow_api_final.model.Phase;
import com.i2s.worfklow_api_final.model.Step;
import com.i2s.worfklow_api_final.repository.PhaseRepository;
import com.i2s.worfklow_api_final.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StepService {
    private final StepRepository stepRepository;
    private final PhaseRepository phaseRepository;

    @Autowired
    public StepService(StepRepository stepRepository, PhaseRepository phaseRepository) {
        this.stepRepository = stepRepository;
        this.phaseRepository = phaseRepository;
    }

    public List<StepDTO> getAllSteps() {
        return stepRepository.findAll().stream().map(StepDTO::new).collect(Collectors.toList());
    }

    public Optional<StepDTO> getStepById(long id) {
        return stepRepository.findById(id).map(StepDTO::new);
    }

    public List<StepDTO> getStepsByPhase(@Valid Phase phase) {
        return stepRepository.findByPhase(phase).stream().map(StepDTO::new).collect(Collectors.toList());
    }

    public StepDTO saveStep(@Valid StepDTO stepDTO) {
        return new StepDTO(stepRepository.save(new Step(stepDTO)));
    }

    public StepDTO updateStep(long id, @Valid StepDTO newStepDTO) {
        Step updatedStep = stepRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Step with ID " + id + " not found"));
        updatedStep.setStepName(newStepDTO.getStepName());
        updatedStep.setDescription(newStepDTO.getDescription());
//            updatedStep.setPhase(newStepDTO.getPhase());
        return new StepDTO(stepRepository.save(updatedStep));
    }

    public void deleteStep(long id) {
        Step step = stepRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Step with ID " + id + " not found."));
        stepRepository.delete(step);
    }


    public StepDTO createStep(long phaseId, @Valid StepDTO stepDTO) {
        Phase phase = phaseRepository.findById(phaseId).orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + phaseId));
        Step step = new Step(stepDTO);
        step.setPhase(phase);
        return new StepDTO(stepRepository.save(step));
    }

    public List<StepDTO> getStepsByPhaseId(long id) {
        Phase phase = phaseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Phase with ID " + id + " not found."));
        return stepRepository.findByPhase(phase).stream().map(StepDTO::new).collect(Collectors.toList());
    }

}
