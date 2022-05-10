package com.example.scoredei.services;

import java.util.*;

import com.example.scoredei.data.Team;
import com.example.scoredei.repositories.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public void addTeam(Team team) {
        teamRepository.save(team);
    }

    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

}
