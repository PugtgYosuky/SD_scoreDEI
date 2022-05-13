package com.example.scoredei.services;

import java.util.*;

import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.repositories.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired PlayerService playerService;

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

    public void deleteTeam(int id) {
        Optional<Team> team = teamRepository.findById(id);
        if(team.isPresent()) {
            for (Player player : team.get().getPlayers()) {
                playerService.deletePlayer(player);
            }
        }
    }
}
