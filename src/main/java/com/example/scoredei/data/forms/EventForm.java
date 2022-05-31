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

    /**
     * This function returns the team of the player
     * 
     * @return The team object.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * This function sets the team of the player
     * 
     * @param team The team that the player is on.
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * This function returns the game object
     * 
     * @return The game object.
     */
    public Game getGame() {
        return game;
    }

    /**
     * This function sets the game variable to the game variable passed in
     * 
     * @param game The game object that the player is in.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * This function returns the player object
     * 
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * This function sets the player variable to the player variable passed in
     * 
     * @param player The player that is being checked.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

}