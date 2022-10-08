package com.example.backenddemo.scheduler;

import com.example.backenddemo.domain.Bid;
import com.example.backenddemo.domain.Job;
import com.example.backenddemo.domain.User;
import com.example.backenddemo.repository.BidRepository;
import com.example.backenddemo.repository.JobRepository;
import com.example.backenddemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JobScheduler {
    @Autowired
    JobRepository jobRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BidRepository bidRepository;

    /**
     * Scheduler to run every 1 second
     * Checks for Open jobs that have expired
     * Sets expired jobs to Closed and sets a winner if possible
     */
    @Scheduled(fixedRate = 1000)
    public void closeJobs() {
        List<Job> jobsToClose = jobRepository.findAllExpiredJobs(LocalDateTime.now());
        jobsToClose.forEach(jobToClose -> {
            jobToClose.setClosed(true);

            if (jobToClose.getLowestBid() == null) {
                jobToClose.setWinner("No Winner");
            } else {
                Bid highestBid = bidRepository.findLowestBid(jobToClose.getId());
                try {
                    Optional<User> userOptional = userRepository.findById(highestBid.getUserId());
                    if (userOptional.isPresent()) {
                        jobToClose.setWinner(userOptional.get().getName());
                    } else {
                        jobToClose.setWinner("No Winner");
                    }
                } catch (Exception ex) {
                    jobToClose.setWinner("No Winner");
                }
            }
            jobRepository.save(jobToClose);
        });
    }
}
