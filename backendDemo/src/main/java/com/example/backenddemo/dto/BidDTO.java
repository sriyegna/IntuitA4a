package com.example.backenddemo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class BidDTO {
    private final long userId;

    private final long jobId;

    private final float bidAmount;
}
