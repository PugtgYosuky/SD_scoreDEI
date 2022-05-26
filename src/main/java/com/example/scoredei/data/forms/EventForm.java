package com.example.scoredei.data.forms;

import com.example.scoredei.data.Team;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Game;

public class EventForm {

    private Team team;

    private Game game;

    private Player player;

    public EventForm() {

    }

    public EventForm(Game game){
        this.game = game;
    }

    public EventForm(Team team, Game game, Player player) {
        this.team = team;
        this.game = game;
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}