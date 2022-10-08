package com.example.backenddemo.repository;

import com.example.backenddemo.domain.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JobRepository extends PagingAndSortingRepository<Job, Long> {

    @Query(value = "SELECT * FROM TBL_JOBS j WHERE j.closed = false AND j.expiration_time < :currentTime", nativeQuery = true)
    List<Job> findAllExpiredJobs(LocalDateTime currentTime);

    @Query(value = "SELECT * FROM TBL_JOBS j WHERE j.closed = false ORDER BY number_of_bids DESC", nativeQuery = true)
    List<Job> findTenActive();
}
