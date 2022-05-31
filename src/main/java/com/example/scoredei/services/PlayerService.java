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

    /**
     * It adds a player to the database.
     * 
     * @param player The player object that is being passed in.
     */
    public void addPlayer(Player player) {
        playerRepository.save(player);
    }
    
    /**
     * For each player in the database, add it to the list of players.
     * 
     * @return A list of players
     */
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        return players;
    }

    /**
     * Find the player by id
     * 
     * @param id The id of the player you want to get.
     * @return An Optional object.
     */
    public Optional<Player> getPlayer(int id) {
        return this.playerRepository.findById(id);
    }

    /**
     * This function deletes a player from the database
     * 
     * @param id the id of the player to be deleted
     * @return A boolean value.
     */
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

    /**
     * This function deletes a player from the database
     * 
     * @param player The player object to be deleted.
     * @return A boolean value.
     */
    public boolean deletePlayer(Player player) {
        return deletePlayer(player.getId());
    }
}
