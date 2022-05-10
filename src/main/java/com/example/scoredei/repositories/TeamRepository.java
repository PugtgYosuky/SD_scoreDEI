package com.example.scoredei.repositories;

import com.example.scoredei.data.Team;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    
}
