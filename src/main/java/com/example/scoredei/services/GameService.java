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

    public void addGame(Game game) {
        gameRepository.save(game);
    }

    public List<Game> getGames() {
        List<Game> games = new ArrayList<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    public Optional<Game> getGame(int id) {
        return this.gameRepository.findById(id);
    }

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

    public boolean deleteGame(Game game) {
        return this.deleteGame(game.getId());
    }

}
