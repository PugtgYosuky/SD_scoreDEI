package com.example.scoredei.repositories;

import com.example.scoredei.data.Player;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    
}
