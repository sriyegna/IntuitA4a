package com.example.backenddemo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class UserDTO {
    private final String name;

    private final String password;

    private final String actorType;
}
