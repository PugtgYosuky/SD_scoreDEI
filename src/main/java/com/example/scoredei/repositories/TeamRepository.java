package com.example.scoredei.repositories;

import com.example.scoredei.data.Team;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Integer> {

    /**
     * It returns a team with the given name.
     * 
     * @param name The name of the method.
     * @return Optional<Team>
     */
    @Query("SELECT t FROM Team t WHERE t.name = ?1")
    Optional<Team> findByName(String name);
    
}
