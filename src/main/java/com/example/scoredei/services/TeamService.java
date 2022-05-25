package com.example.scoredei.services;

import java.util.*;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.repositories.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired 
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    public void addTeam(Team team) {
        teamRepository.save(team);
    }

    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    public Optional<Team> getTeam(int id) {
        return teamRepository.findById(id);
    }

    public boolean deleteTeam(int id) {
        Optional<Team> t = teamRepository.findById(id);
        if(!t.isPresent()) 
            return false;
        
        Team team = t.get();
        for (Player player : team.getPlayers()) 
            playerService.deletePlayer(player);
        
        for (Game game : team.getGames()) 
            gameService.deleteGame(game);
        
        try {
            this.teamRepository.deleteById(id);
        }catch ( IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
