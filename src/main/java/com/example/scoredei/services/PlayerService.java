package com.example.scoredei.services;

import java.util.*;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameService gameService;

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

    public boolean deletePlayer(int id) {
        Optional<Player> player = this.playerRepository.findById(id);
        if(player.isEmpty()) {
            return false;
        }
        Player p = player.get();
        try {
            for(Game game : p.getTeam().getGames())
                gameService.deleteGame(game.getId());
            this.playerRepository.deleteById(id);
        }catch ( IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public boolean deletePlayer(Player player) {
        return deletePlayer(player.getId());
    }
}
