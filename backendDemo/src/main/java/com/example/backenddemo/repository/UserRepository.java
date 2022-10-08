package com.example.backenddemo.repository;

import com.example.backenddemo.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByName(String name);
}
