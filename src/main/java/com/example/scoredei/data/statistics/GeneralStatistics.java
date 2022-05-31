package com.example.scoredei.data.statistics;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.events.EventGoal;
import com.example.scoredei.data.types.EventType;
import com.example.scoredei.services.GameService;
import com.example.scoredei.services.TeamService;

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

    private Map<Player, Integer> goals;

    private Player bestScorer;
    
    private int maxGoals;

    // A constructor.
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
        this.goals = new HashMap<>();
        this.bestScorer = null;
        this.maxGoals = 0;
        this.process();
    }

    /**
     * It processes the games and calculates the statistics for each team
     */
    private void process() {
        for(Game game : this.gameService.getGames()) {
            if(!game.isFinish()) {
                continue;
            }
            GameStatistics gameStatistics = new GameStatistics(game);
            this.processBestScorer(game);
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

    /**
     * This function iterates through the events of a game and if the event is a goal, it increments
     * the number of goals of the player who scored the goal. If the number of goals of the player is
     * greater than the maximum number of goals, the player becomes the best scorer
     * 
     * @param game the game to be processed
     */
    private void processBestScorer(Game game){
        int aux;
        for(Event event : game.getEvents()) {
            if(event.getType() == EventType.GOAL) {
                EventGoal eventGoal = (EventGoal) event;
                Player player = eventGoal.getPlayer();
                aux = goals.getOrDefault(player, 0) + 1;
                goals.put(player, aux);
                if(aux > maxGoals) {
                    maxGoals = aux;
                    bestScorer = player;
                }
            }   
        }
    }

    /**
     * This function returns the best scorer of the team
     * 
     * @return The best scorer of the team.
     */
    public Player getBestScorer() {
        return this.bestScorer;
    }
    
    /**
     * If the team is in the map, return the number of wins for that team. Otherwise, return 0
     * 
     * @param team The team to get the number of wins for.
     * @return The number of wins for a team.
     */
    public int getWins(Team team) {
        return this.numberOfWins.getOrDefault(team, 0);
    }

    /**
     * If the team has a number of draws, return that number. Otherwise, return 0
     * 
     * @param team The team to get the number of draws for.
     * @return The number of draws for a team.
     */
    public int getDraws(Team team) {
        return this.numberOfDraws.getOrDefault(team, 0);
    }

    /**
     * This function returns the number of defeats of a team
     * 
     * @param team The team to get the number of defeats for.
     * @return The number of defeats for a team.
     */
    public int getDefeats(Team team) {
        return this.numberOfDefeats.getOrDefault(team, 0);
    }

    /**
     * If the team has scored goals, return the number of goals scored. Otherwise, return 0
     * 
     * @param team The team to get the number of goals scored for
     * @return The number of goals scored by the team.
     */
    public int getGoalsScored(Team team) {
        return this.numberOfGoalsScored.getOrDefault(team, 0);
    }

    /**
     * If the team is in the map, return the value, otherwise return 0
     * 
     * @param team The team to get the goals conceded for.
     * @return The number of goals conceded by the team.
     */
    public int getGoalsConceded(Team team) {
        return this.numberOfGoalsConceded.getOrDefault(team, 0);
    }

    /**
     * If the team is in the map, return the value associated with the team, otherwise return 0
     * 
     * @param team The team to get the points of
     * @return The teamPoints.getOrDefault(team, 0) is being returned.
     */
    public int getTeamPoints(Team team) {
        return this.teamPoints.getOrDefault(team, 0);
    }

    /**
     * If the team is in the map, return the number of games for that team. Otherwise, return 0
     * 
     * @param team The team to get the number of games for.
     * @return The number of games played by a team.
     */
    public int getNumberOfGames(Team team) {
        return this.numberOfGames.getOrDefault(team, 0);
    }

}