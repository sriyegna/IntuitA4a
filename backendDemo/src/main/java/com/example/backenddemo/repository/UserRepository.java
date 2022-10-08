package com.example.backenddemo.repository;

import com.example.backenddemo.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);
}
