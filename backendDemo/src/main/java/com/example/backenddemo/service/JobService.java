package com.example.backenddemo.service;

import com.example.backenddemo.domain.Job;
import com.example.backenddemo.domain.User;
import com.example.backenddemo.dto.JobDTO;
import com.example.backenddemo.dto.UserDTO;
import com.example.backenddemo.repository.JobRepository;
import com.example.backenddemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    public void saveJob(JobDTO jobDTO) {
        Job job = Job
                .builder()
                .created(LocalDateTime.now())
                .userId(jobDTO.getUserId())
                .description(jobDTO.getDescription())
                .requirements(jobDTO.getRequirements())
                .name(jobDTO.getName())
                .contact(jobDTO.getContact())
                .lowestBid(jobDTO.getLowestBid())
                .numberOfBids(jobDTO.getNumberOfBids())
                .expirationTime(jobDTO.getExpirationTime())
                .build();
        jobRepository.save(job);
    }

    public List<Job> findTenRecent() {
        return jobRepository.findTop10ByOrderByCreatedDesc();
    }
}
