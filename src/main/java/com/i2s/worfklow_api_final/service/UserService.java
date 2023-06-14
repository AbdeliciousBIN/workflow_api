package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.dto.JobDTO;
import com.i2s.worfklow_api_final.dto.UserDTO;
import com.i2s.worfklow_api_final.model.Job;
import com.i2s.worfklow_api_final.model.Role;
import com.i2s.worfklow_api_final.model.User;
import com.i2s.worfklow_api_final.repository.JobRepository;
import com.i2s.worfklow_api_final.repository.RoleRepository;
import com.i2s.worfklow_api_final.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, JobRepository jobRepository, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> getAllUsers() {
//        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(long id) {
        return userRepository.findById(id).map(user -> modelMapper.map(user, UserDTO.class));
    }


    public UserDTO createUser(@Valid UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        // Set the Job for the user (if jobId is provided)
        Job job = jobRepository.findById(userDTO.getJobId()).orElseThrow(() -> new EntityNotFoundException("Job with ID " + userDTO.getJobId() + " not found"));
        user.setJob(job);
        Role role = roleRepository.findRoleByName(userDTO.getRoleName()).orElseThrow(() -> new EntityNotFoundException("Role with name " + userDTO.getRoleName() + " not found"));
        user.setRole(role);
        // Hash the user's password using bcrypt
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        User savedUser = userRepository.save(user);

        savedUser.setPasswordHash(null);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found."));
        userRepository.deleteById(id);
    }

    public Optional<JobDTO> getJobByUserId(long id) {
        return userRepository.findById(id)
                .map(User::getJob)
                .map(job -> {
                    JobDTO jobDTO = modelMapper.map(job, JobDTO.class);
                    List<Long> userIds = job.getUsers().stream().map(User::getId).collect(Collectors.toList());
                    jobDTO.setUsersId(userIds);
                    return jobDTO;
                });
    }

    public UserDTO getUserByFullName(String fullName) {
        User user = userRepository.findByFullName(fullName)
                .orElseThrow(() -> new EntityNotFoundException("User with name '" + fullName + "' not found."));
        return modelMapper.map(user, UserDTO.class);
    }

}
