package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.dto.UserDTO;
import com.i2s.worfklow_api_final.model.Job;
import com.i2s.worfklow_api_final.model.User;
import com.i2s.worfklow_api_final.repository.JobRepository;
import com.i2s.worfklow_api_final.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, JobRepository jobRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDTO> getAllUsers() {
//        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(long id) {
        return userRepository.findById(id).map(user -> modelMapper.map(user, UserDTO.class));
    }

    //    public UserDTO createStep(@Valid TaskDTO taskDTO) {
//        Long stepId = Optional.ofNullable(taskDTO).map(TaskDTO::getStepId).orElseThrow(() -> new IllegalArgumentException("TaskDTO is null or doesn't have a stepId field"));
//        Step step = stepRepository.findById(stepId).orElseThrow(() -> new EntityNotFoundException("Step with ID " + stepId + " not found"));
//        Task task = new Task();
//        task.setTaskName(taskDTO.getTaskName());
//    }
    public UserDTO createUser(@Valid UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        // Set the Job for the user (if jobId is provided)
        Job job = jobRepository.findById(userDTO.getJobId()).orElseThrow(() -> new EntityNotFoundException("Job with ID " + userDTO.getJobId() + " not found"));
        user.setJob(job);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);

    }
}
