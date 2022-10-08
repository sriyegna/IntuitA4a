package com.example.backenddemo.repository;

import com.example.backenddemo.domain.Bid;
import com.example.backenddemo.domain.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BidRepository extends CrudRepository<Bid, Long> {
}
