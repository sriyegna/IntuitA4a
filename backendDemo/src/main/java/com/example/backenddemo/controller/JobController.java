package com.example.backenddemo.controller;

import com.example.backenddemo.domain.Job;
import com.example.backenddemo.dto.JobDTO;
import com.example.backenddemo.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller to handle actions with the Job Service and Job Repository
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("job")
public class JobController {

    @Autowired
    JobService jobService;

    /**
     * Finds a Job by the id from jobRepository
     *
     * @param id
     * @return ResponseEntity<?> - Job || String error message
     */
    @GetMapping("")
    public ResponseEntity<?> getJobById(@RequestParam String id) {
        Optional<Job> job = jobService.findById(Long.parseLong(id));
        if (job.isPresent()) {
            return new ResponseEntity<>(job.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(String.format("Job with id: %s not found", id), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Posts a Job to jobRepository using given jobDTO
     *
     * @param jobDTO
     * @return ResponseEntity<?> - Job
     */
    @PostMapping("/post")
    public ResponseEntity<?> postJob(@RequestBody JobDTO jobDTO) {
        try {
            Job job = jobService.createJob(jobDTO);
            return new ResponseEntity<>(job, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            String[] longError = NestedExceptionUtils.getMostSpecificCause(ex).getMessage().split(";");
            return new ResponseEntity<>(longError[0], HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Finds ten most recently posted jobs.
     *
     * @return ResponseEntity<List < Job>>
     */
    @GetMapping("/tenRecent")
    public ResponseEntity<List<Job>> getTenRecent() {
        List<Job> tenRecentJobs = jobService.findTenRecent();
        return new ResponseEntity<>(tenRecentJobs, HttpStatus.OK);
    }

    /**
     * Finds ten most recently active jobs.
     * Active is decided by Open postings and number of bids
     *
     * @return ResponseEntity<List < Job>>
     */
    @GetMapping("/tenActive")
    public ResponseEntity<List<Job>> getTenActive() {
        List<Job> tenMostActive = jobService.findTenMostActive();
        return new ResponseEntity<>(tenMostActive, HttpStatus.OK);
    }
}
