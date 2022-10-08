package com.example.backenddemo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
public class JobDTO {
    private final long userId;

    private final String description;

    private final String requirements;

    private final String name;

    private final String contact;

    private final float lowestBid;

    private final int numberOfBids;

    private LocalDateTime expirationTime;

    private LocalDateTime created;
}
