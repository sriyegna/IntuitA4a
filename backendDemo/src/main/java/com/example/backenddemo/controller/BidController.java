package com.example.backenddemo.controller;

import com.example.backenddemo.dto.BidDTO;
import com.example.backenddemo.dto.JobDTO;
import com.example.backenddemo.service.BidService;
import com.example.backenddemo.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BidController {

    @Autowired
    BidService bidService;

//    @GetMapping("/user")
//    public User getUser(@RequestParam(value = "name") String name) {
//        return new User(name, "user");
//    }

    @PostMapping("/bid")
    public ResponseEntity<BidDTO> postUser(@RequestBody BidDTO bidDTO) throws Exception {
        bidService.saveBid(bidDTO);
        return new ResponseEntity<>(bidDTO, HttpStatus.CREATED);
    }
}
