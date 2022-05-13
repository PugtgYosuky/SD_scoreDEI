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

    public Optional<Player> getPlayer(int id) {
        return this.playerRepository.findById(id);
    }

    public void deletePlayer(int id) {
        this.playerRepository.deleteById(id);
    }

    public void deletePlayer(Player player) {
        this.playerRepository.delete(player);
    }
}
