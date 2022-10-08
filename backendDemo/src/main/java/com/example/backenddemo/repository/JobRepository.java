package com.example.backenddemo.repository;

import com.example.backenddemo.domain.Job;
import com.example.backenddemo.dto.JobDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends CrudRepository<Job, Long> {

    public List<Job> findTop10ByOrderByCreatedDesc();
}
