package com.example.backenddemo.controller;

import com.example.backenddemo.domain.Job;
import com.example.backenddemo.dto.JobDTO;
import com.example.backenddemo.dto.UserDTO;
import com.example.backenddemo.service.JobService;
import com.example.backenddemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("job")
public class JobController {

    @Autowired
    JobService jobService;

//    @GetMapping("/user")
//    public User getUser(@RequestParam(value = "name") String name) {
//        return new User(name, "user");
//    }

    @PostMapping("/post")
    public ResponseEntity<JobDTO> postUser(@RequestBody JobDTO jobDTO) throws Exception {
        jobService.saveJob(jobDTO);
        return new ResponseEntity<>(jobDTO, HttpStatus.CREATED);
    }

    @GetMapping("/tenRecent")
    public ResponseEntity<List<Job>> getTenRecent() {
        List<Job> tenRecentJobs = jobService.findTenRecent();
        return new ResponseEntity<>(tenRecentJobs, HttpStatus.OK);
    }
}
