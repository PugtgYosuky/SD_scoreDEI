package com.example.scoredei.services;

import java.util.*;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.events.EventGoal;
import com.example.scoredei.data.types.EventType;
import com.example.scoredei.repositories.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

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
        if(!g.isPresent()) 
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

    public Player getBestScorer() {
        Map<Player, Integer> goals = new HashMap<>();
        Player bestScorer = null;
        int maxGoals = 0;
        int aux;
        for(Game game : this.getGames()) {
            for(Event event : game.getEvents()) {
                if(event.getType() == EventType.GOAL) {
                    EventGoal eventGoal = (EventGoal) event;
                    Player player= eventGoal.getPlayer();
                    aux = goals.getOrDefault(player, 0) + 1;
                    goals.put(player, aux);
                    if(aux > maxGoals) {
                        maxGoals = aux;
                        bestScorer = player;
                    }
                }   
            }
        }
        return bestScorer;
    }

}
