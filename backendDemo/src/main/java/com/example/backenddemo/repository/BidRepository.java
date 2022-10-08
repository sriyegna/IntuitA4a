package com.example.backenddemo.repository;

import com.example.backenddemo.domain.Bid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BidRepository extends PagingAndSortingRepository<Bid, Long> {

    @Query(value = "SELECT * FROM TBL_BIDS b WHERE b.job_id = :jobId ORDER BY b.bid_amount ASC LIMIT 1", nativeQuery = true)
    Bid findLowestBid(long jobId);
}
