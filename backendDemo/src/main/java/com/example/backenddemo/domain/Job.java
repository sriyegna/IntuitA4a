package com.example.backenddemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_JOBS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private long userId;
    @Column(nullable = false)
    @Size(max = 16384)
    private String description;
    @Column(nullable = false)
    @Size(max = 16384)
    private String requirements;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String contact;
    @Column
    private Float lowestBid;
    @Column(nullable = false)
    private int numberOfBids;
    @Column(nullable = false)
    private LocalDateTime expirationTime;
    @Column(nullable = false)
    private LocalDateTime created;
    @Column(nullable = false)
    private boolean closed;
    @Column
    private String winner;
}