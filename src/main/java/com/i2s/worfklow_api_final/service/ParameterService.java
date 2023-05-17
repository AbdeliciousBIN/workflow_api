package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.dto.ParameterDTO;
import com.i2s.worfklow_api_final.model.Parameter;
import com.i2s.worfklow_api_final.repository.ParameterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParameterService {

    private final ParameterRepository parameterRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ParameterService(ParameterRepository parameterRepository, ModelMapper modelMapper) {
        this.parameterRepository = parameterRepository;
        this.modelMapper = modelMapper;
    }

    public List<ParameterDTO> getAllParameters() {
        List<Parameter> parameters = parameterRepository.findAll();
        return parameters.stream().map(parameter -> modelMapper.map(parameter, ParameterDTO.class)).collect(Collectors.toList());
    }

    public Optional<ParameterDTO> getParameterById(long id) {
        return parameterRepository.findById(id).map(parameter -> modelMapper.map(parameter, ParameterDTO.class));
    }

    public ParameterDTO createParameter(ParameterDTO parameterDTO) {
        Parameter parameter = modelMapper.map(parameterDTO, Parameter.class);
        Parameter savedParameter = parameterRepository.save(parameter);
        return modelMapper.map(savedParameter, ParameterDTO.class);
    }

    public void deleteParameter(long id) {
        Parameter parameter = parameterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Parameter with ID " + id + " not found."));
        parameterRepository.deleteById(id);
    }
}
