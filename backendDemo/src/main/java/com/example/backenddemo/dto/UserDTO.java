package com.example.backenddemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private final String name;

    private final String password;

    private final String actorType;
}
