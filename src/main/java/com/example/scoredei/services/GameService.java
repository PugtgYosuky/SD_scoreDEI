package com.example.scoredei.services;

import java.util.*;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.repositories.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EventService eventService;

    /**
     * This function takes a game object and saves it to the database
     * 
     * @param game The game object that is being added to the database.
     */
    public void addGame(Game game) {
        gameRepository.save(game);
    }

    /**
     * For each game in the database, add it to the list of games.
     * 
     * @return A list of games
     */
    public List<Game> getGames() {
        List<Game> games = new ArrayList<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    /**
     * Find the game by id
     * 
     * @param id The id of the game you want to get.
     * @return An Optional object.
     */
    public Optional<Game> getGame(int id) {
        return this.gameRepository.findById(id);
    }

    /**
     * It deletes a game from the database, and all of the events associated with that game
     * 
     * @param id the id of the game to be deleted
     * @return A boolean value.
     */
    public boolean deleteGame(int id) {
        Optional<Game> g = this.gameRepository.findById(id);
        if(g.isEmpty())
            return false;
        
        Game game = g.get();
        for(Event event : game.getEvents())
            this.eventService.deleteEvent(event);
        
        try {
            this.gameRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * This function deletes a game from the database
     * 
     * @param game The game object to be deleted.
     * @return A boolean value.
     */
    public boolean deleteGame(Game game) {
        return this.deleteGame(game.getId());
    }

}
