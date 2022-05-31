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

    /**
     * This function adds a team to the database
     * 
     * @param team The team object that is being passed in.
     */
    public void addTeam(Team team) {
        teamRepository.save(team);
    }

    /**
     * Get all the teams from the database and add them to a list.
     * 
     * @return A list of teams
     */
    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    /**
     * Find the team by name
     * 
     * @param name The name of the team to be retrieved.
     * @return Optional<Team>
     */
    public Optional<Team> getTeam(String name) {
        return teamRepository.findByName(name);
    }

    /**
     * Find the team by id
     * 
     * @param id The id of the team you want to get.
     * @return An Optional object.
     */
    public Optional<Team> getTeam(int id) {
        return teamRepository.findById(id);
    }

    /**
     * It deletes a team, and all of the games and players associated with that team
     * 
     * @param id the id of the team to be deleted
     * @return A boolean value.
     */
    public boolean deleteTeam(int id) {
        Optional<Team> t = teamRepository.findById(id);
        if(t.isEmpty())
            return false;
        
        Team team = t.get();

        for (Game game : team.getGames())
            gameService.deleteGame(game);

        for (Player player : team.getPlayers())
            playerService.deletePlayer(player);

        try {
            this.teamRepository.deleteById(id);
        }catch ( IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
