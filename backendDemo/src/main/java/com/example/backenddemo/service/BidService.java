package com.example.backenddemo.service;

import com.example.backenddemo.domain.Bid;
import com.example.backenddemo.domain.User;
import com.example.backenddemo.dto.BidDTO;
import com.example.backenddemo.dto.UserDTO;
import com.example.backenddemo.repository.BidRepository;
import com.example.backenddemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {

    @Autowired
    BidRepository bidRepository;

    public void saveBid(BidDTO bidDTO) {
        Bid bid = Bid
                .builder()
                .userId(bidDTO.getUserId())
                .jobId(bidDTO.getJobId())
                .bidAmount(bidDTO.getBidAmount())
                .build();
        bidRepository.save(bid);

    }
}
