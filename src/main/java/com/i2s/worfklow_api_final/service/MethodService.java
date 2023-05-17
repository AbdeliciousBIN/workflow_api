package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.dto.MethodDTO;
import com.i2s.worfklow_api_final.model.Method;
import com.i2s.worfklow_api_final.repository.MethodRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MethodService {

    private final MethodRepository methodRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public MethodService(MethodRepository methodRepository, ModelMapper modelMapper) {
        this.methodRepository = methodRepository;
        this.modelMapper = modelMapper;
    }

    public List<MethodDTO> getAllMethods() {
        List<Method> methods = methodRepository.findAll();
        return methods.stream().map(method -> modelMapper.map(method, MethodDTO.class)).collect(Collectors.toList());
    }

    public Optional<MethodDTO> getMethodById(long id) {
        return methodRepository.findById(id).map(method -> modelMapper.map(method, MethodDTO.class));
    }

    public MethodDTO createMethod(MethodDTO methodDTO) {
        Method method = modelMapper.map(methodDTO, Method.class);
        Method savedMethod = methodRepository.save(method);
        return modelMapper.map(savedMethod, MethodDTO.class);
    }

    public void deleteMethod(long id) {
        Method method = methodRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Method with ID " + id + " not found."));
        methodRepository.deleteById(id);
    }
}
