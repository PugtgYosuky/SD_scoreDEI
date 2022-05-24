package com.example.scoredei.data.statistics;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Team;
import com.example.scoredei.services.GameService;
import com.example.scoredei.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class GeneralStatistics {

    private GameService gameService;

    private TeamService teamService;

    private Map<Team, Integer> numberOfWins;

    private Map<Team, Integer> numberOfDraws;

    private Map<Team, Integer> numberOfDefeats;

    private Map<Team, Integer> numberOfGoalsScored;

    private Map<Team, Integer> numberOfGoalsConceded;

    private Map<Team, Integer> teamPoints;

    private Map<Team, Integer> numberOfGames;

    public GeneralStatistics(GameService gameService, TeamService teamService) {
        this.gameService = gameService;
        this.teamService = teamService;
        this.numberOfWins = new HashMap<>();
        this.numberOfDraws = new HashMap<>();
        this.numberOfDefeats = new HashMap<>();
        this.numberOfGoalsScored = new HashMap<>();
        this.numberOfGoalsConceded = new HashMap<>();
        this.teamPoints = new HashMap<>();
        this.numberOfGames = new HashMap<>();
        this.process();
    }

    private void process() {
        // CHECK IF THE GAME IS OVER
        for(Game game : this.gameService.getGames()) {
            GameStatistics gameStatistics = new GameStatistics(game);

            int teamAGoals = gameStatistics.getTeamGoals(game.getTeamA()).size();
            int teamBGoals = gameStatistics.getTeamGoals(game.getTeamB()).size();

            this.numberOfGoalsScored.put(game.getTeamA(), this.numberOfGoalsScored.getOrDefault(game.getTeamA(), 0) + teamAGoals);
            this.numberOfGoalsScored.put(game.getTeamB(), this.numberOfGoalsScored.getOrDefault(game.getTeamB(), 0) + teamBGoals);
            this.numberOfGoalsConceded.put(game.getTeamA(), this.numberOfGoalsConceded.getOrDefault(game.getTeamA(), 0) + teamBGoals);
            this.numberOfGoalsConceded.put(game.getTeamB(), this.numberOfGoalsConceded.getOrDefault(game.getTeamB(), 0) + teamAGoals);
            
            this.numberOfGames.put(game.getTeamA(), this.numberOfGames.getOrDefault(game.getTeamA(), 0) + 1);
            this.numberOfGames.put(game.getTeamB(), this.numberOfGames.getOrDefault(game.getTeamB(), 0) + 1);

            if(teamAGoals > teamBGoals){
                this.numberOfWins.put(game.getTeamA(), this.numberOfWins.getOrDefault(game.getTeamA(), 0) + 1);
                this.numberOfDefeats.put(game.getTeamB(), this.numberOfDefeats.getOrDefault(game.getTeamB(), 0) + 1);
                this.teamPoints.put(game.getTeamA(), this.teamPoints.getOrDefault(game.getTeamA(), 0) + 3);
            } else if (teamAGoals < teamBGoals){
                this.numberOfDefeats.put(game.getTeamA(), this.numberOfDefeats.getOrDefault(game.getTeamA(), 0) + 1);
                this.numberOfWins.put(game.getTeamB(), this.numberOfWins.getOrDefault(game.getTeamB(), 0) + 1);
                this.teamPoints.put(game.getTeamB(), this.teamPoints.getOrDefault(game.getTeamB(), 0) + 3);
            } else {
                this.numberOfDraws.put(game.getTeamA(), this.numberOfDraws.getOrDefault(game.getTeamA(), 0) + 1);
                this.numberOfDraws.put(game.getTeamB(), this.numberOfDraws.getOrDefault(game.getTeamB(), 0) + 1);
                this.teamPoints.put(game.getTeamA(), this.teamPoints.getOrDefault(game.getTeamA(), 0) + 1);
                this.teamPoints.put(game.getTeamB(), this.teamPoints.getOrDefault(game.getTeamB(), 0) + 1);
            }

        }
    }

    public int getWins(Team team) {
        return this.numberOfWins.getOrDefault(team, 0);
    }

    public int getDraws(Team team) {
        return this.numberOfDraws.getOrDefault(team, 0);
    }

    public int getDefeats(Team team) {
        return this.numberOfDefeats.getOrDefault(team, 0);
    }

    public int getGoalsScored(Team team) {
        return this.numberOfGoalsScored.getOrDefault(team, 0);
    }

    public int getGoalsConceded(Team team) {
        return this.numberOfGoalsConceded.getOrDefault(team, 0);
    }

    public int getTeamPoints(Team team) {
        return this.teamPoints.getOrDefault(team, 0);
    }

    public int getNumberOfGames(Team team) {
        return this.numberOfGames.getOrDefault(team, 0);
    }

}