package com.example.backenddemo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class JobDTO {
    private final long userId;

    private final String description;

    private final String requirements;

    private final String name;

    private final String contact;

    private final Float lowestBid;

    private final int numberOfBids;

    private final LocalDateTime expirationTime;

    private final boolean closed;

    private final String winner;
}
