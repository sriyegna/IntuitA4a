package com.example.backenddemo.controller;

import com.example.backenddemo.domain.Bid;
import com.example.backenddemo.dto.BidDTO;
import com.example.backenddemo.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to handle actions with the Bidding Service and Bidding Repository
 */
@RestController
@RequestMapping("bid")
@CrossOrigin(origins = "http://localhost:3000")

public class BidController {

    @Autowired
    BidService bidService;

    /**
     * Api route /bid/post
     * Handles posting a bid to the database
     * Will also update the jobs lowestBid and numberOfBids
     *
     * @param bidDTO
     * @return ResponseEntity<Bid | | String error message>
     * @throws Exception
     */
    @PostMapping("/post")
    public ResponseEntity<?> postBid(@RequestBody BidDTO bidDTO) throws Exception {
        if (bidDTO.getBidAmount() >= 0) {
            Bid bid = bidService.createBid(bidDTO);
            return new ResponseEntity<>(bid, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Bid must be over 0", HttpStatus.BAD_REQUEST);
        }
    }
}
