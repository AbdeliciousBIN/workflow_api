package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.dto.JobDTO;
import com.i2s.worfklow_api_final.model.Job;
import com.i2s.worfklow_api_final.model.User;
import com.i2s.worfklow_api_final.repository.JobRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public JobService(JobRepository jobRepository, ModelMapper modelMapper) {
        this.jobRepository = jobRepository;
        this.modelMapper = modelMapper;
    }

    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll().stream().map(job -> {
            JobDTO jobDTO = modelMapper.map(job, JobDTO.class);
            jobDTO.setUsersId(job.getUsers().stream().map(User::getId).collect(Collectors.toList()));
            return jobDTO;
        }).collect(Collectors.toList());
    }

    public Optional<JobDTO> getJobById(long id) {
        return jobRepository.findById(id).map(job -> {
            JobDTO jobDTO = modelMapper.map(job, JobDTO.class);
            jobDTO.setUsersId(job.getUsers().stream().map(User::getId).collect(Collectors.toList()));
            return jobDTO;
        });
    }

    public JobDTO createJob(@Valid JobDTO jobDTO) {
        Job job = modelMapper.map(jobDTO, Job.class);
        Job savedJob = jobRepository.save(job);
        return modelMapper.map(savedJob, JobDTO.class);
    }
}
