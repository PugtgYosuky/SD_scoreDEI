package com.example.scoredei.repositories;

import com.example.scoredei.data.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    
}
