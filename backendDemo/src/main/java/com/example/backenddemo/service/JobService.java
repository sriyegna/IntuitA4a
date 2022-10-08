package com.example.backenddemo.service;

import com.example.backenddemo.domain.Job;
import com.example.backenddemo.dto.JobDTO;
import com.example.backenddemo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    public Job createJob(JobDTO jobDTO) {
        Job job = Job
                .builder()
                .created(LocalDateTime.now())
                .userId(jobDTO.getUserId())
                .description(jobDTO.getDescription())
                .requirements(jobDTO.getRequirements())
                .name(jobDTO.getName())
                .contact(jobDTO.getContact())
                .lowestBid(null)
                .numberOfBids(jobDTO.getNumberOfBids())
                .expirationTime(jobDTO.getExpirationTime())
                .winner(null)
                .closed(false)
                .build();
        jobRepository.save(job);
        return job;
    }

    public List<Job> findTenRecent() {
        return jobRepository.findAll(PageRequest.of(0, 10, Sort.by("created").descending())).getContent();
    }

    public List<Job> findTenMostActive() {
        return jobRepository.findTenActive();
    }

    public Optional<Job> findById(long id) {
        return jobRepository.findById(id);
    }
}
