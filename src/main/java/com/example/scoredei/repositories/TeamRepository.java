package com.example.scoredei.repositories;

import com.example.scoredei.data.Team;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Integer> {

    @Query("SELECT t FROM Team t WHERE t.name = ?1")
    Optional<Team> findByName(String name);
    
}
