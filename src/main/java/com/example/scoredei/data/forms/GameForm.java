package com.example.scoredei.data.forms;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Team;

public class GameForm {
    private Game game;
    private Team teamA;
    private Team teamB;

    public GameForm(){
        this.game = new Game();
        this.game.setLocation("Teste");
        this.teamA = new Team();
        this.teamB = new Team();
    }

    public GameForm(Game game) {
        this.game = game;
        this.teamA = game.getTeamA();
        this.teamB = game.getTeamB();
    }

    /**
     * This function returns the game object
     * 
     * @return The game object.
     */
    public Game getGame(){
        return game;
    }
    
    /**
     * This function sets the game variable to the game variable passed in
     * 
     * @param game The game object that the player is in.
     */
    public void setGame(Game game){
        this.game = game;
    }

    /**
     * This function returns the teamA variable
     * 
     * @return The teamA object.
     */
    public Team getTeamA(){
        return teamA;
    }

    /**
     * This function sets the teamA variable to the teamA parameter
     * 
     * @param teamA The team that is playing at home.
     */
    public void setTeamA(Team teamA){
        this.teamA = teamA;
    }

    /**
     * This function returns the teamB object
     * 
     * @return The teamB object.
     */
    public Team getTeamB(){
        return teamB;
    }

    /**
     * This function sets the teamB variable to the teamB parameter
     * 
     * @param teamB The team that is playing against the team that is being set.
     */
    public void setTeamB(Team teamB){
        this.teamB = teamB;
    }
}
