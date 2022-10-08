package com.example.backenddemo.service;

import com.example.backenddemo.domain.Bid;
import com.example.backenddemo.domain.Job;
import com.example.backenddemo.dto.BidDTO;
import com.example.backenddemo.repository.BidRepository;
import com.example.backenddemo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BidService {

    @Autowired
    BidRepository bidRepository;
    @Autowired
    JobRepository jobRepository;

    public Bid createBid(BidDTO bidDTO) throws Exception {
        Optional<Job> jobOptional = jobRepository.findById(bidDTO.getJobId());
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setNumberOfBids(job.getNumberOfBids() + 1);
            if (job.getLowestBid() == null || bidDTO.getBidAmount() < job.getLowestBid()) {
                job.setLowestBid(bidDTO.getBidAmount());
            }
            jobRepository.save(job);
            Bid bid = Bid
                    .builder()
                    .created(LocalDateTime.now())
                    .userId(bidDTO.getUserId())
                    .jobId(bidDTO.getJobId())
                    .bidAmount(bidDTO.getBidAmount())
                    .build();
            bidRepository.save(bid);
            return bid;
        } else {
            throw new Exception("Error creating bid.");
        }
    }
}
