package com.example.scoredei.services;

import java.util.*;

import com.example.scoredei.data.Player;
import com.example.scoredei.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public void addPlayer(Player player) {
        playerRepository.save(player);
    }
    
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        return players;
    }

}
