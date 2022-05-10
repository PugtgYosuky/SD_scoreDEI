package com.example.scoredei.services;

import java.util.*;

import com.example.scoredei.data.Game;
import com.example.scoredei.repositories.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public void addGame(Game game) {
        gameRepository.save(game);
    }

    public List<Game> getGames() {
        List<Game> games = new ArrayList<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }
}
