package com.example.scoredei.repositories;

import com.example.scoredei.data.Game;

import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {
    
}
