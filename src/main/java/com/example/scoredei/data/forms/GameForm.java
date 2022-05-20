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

    public Game getGame(){
        return game;
    }
    
    public void setGame(Game game){
        this.game = game;
    }

    public Team getTeamA(){
        return teamA;
    }
    public void setTeamA(Team teamA){
        this.teamA = teamA;
    }

    public Team getTeamB(){
        return teamB;
    }

    public void setTeamB(Team teamB){
        this.teamB = teamB;
    }
}
