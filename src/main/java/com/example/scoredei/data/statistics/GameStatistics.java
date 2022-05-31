package com.example.scoredei.data.statistics;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.events.EventGoal;
import com.example.scoredei.data.events.EventInterrupt;
import com.example.scoredei.data.events.EventRedCard;
import com.example.scoredei.data.events.EventYellowCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStatistics {

    private Game game;
    private final Map<Team, List<EventYellowCard>> yellowCards;
    private final Map<Team, List<EventRedCard>> redCards;
    private final Map<Team, List<EventGoal>> goals;
    private List<EventInterrupt> interrupts;

    // The constructor of the class. It is called when a new object of the class is created.
    public GameStatistics(Game game) {
        this.yellowCards = new HashMap<>();
        this.redCards = new HashMap<>();
        this.goals = new HashMap<>();
        this.interrupts = new ArrayList<>();
        this.game = game;
        this.process();
    }

    /**
     * This function takes a list of events and sorts them into different lists based on their type
     */
    void process() {
        this.yellowCards.put(game.getTeamA(), new ArrayList<>());
        this.yellowCards.put(game.getTeamB(), new ArrayList<>());
        this.redCards.put(game.getTeamA(), new ArrayList<>());
        this.redCards.put(game.getTeamB(), new ArrayList<>());
        this.goals.put(game.getTeamA(), new ArrayList<>());
        this.goals.put(game.getTeamB(), new ArrayList<>());
        this.interrupts = new ArrayList<>();

        EventYellowCard yellowCard;
        EventRedCard redCard;
        EventGoal goal;
        EventInterrupt interrupt;
        for(Event e : game.getEvents()) {
            switch (e.getType()) {
                case GOAL -> {
                    goal = (EventGoal) e;
                    this.goals.get(goal.getTeam()).add(goal);
                }
                case INTERRUPT -> {
                    interrupt = (EventInterrupt) e;
                    this.interrupts.add(interrupt);
                }
                case RED_CARD -> {
                    redCard = (EventRedCard) e;
                    this.redCards.get(redCard.getPlayer().getTeam()).add(redCard);
                }
                case YELLOW_CARD -> {
                    yellowCard = (EventYellowCard) e;
                    this.yellowCards.get(yellowCard.getPlayer().getTeam()).add(yellowCard);
                }
                default -> {
                }
            }
        }
    }

    /**
     * This function returns the game object
     * 
     * @return The game object.
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * This function returns a list of red cards for a given team
     * 
     * @param team The team to get the red cards for.
     * @return A list of EventRedCard objects.
     */
    public List<EventRedCard> getTeamRedCards(Team team) {
        return this.redCards.get(team);
    }

    /**
     * This function returns a list of yellow cards for a given team
     * 
     * @param team The team to get the yellow cards for
     * @return A list of EventYellowCard objects.
     */
    public List<EventYellowCard> getTeamYellowCards(Team team) {
        return this.yellowCards.get(team);
    }

    /**
     * This function returns a list of goals scored by a team
     * 
     * @param team The team to get the goals for
     * @return A list of EventGoal objects.
     */
    public List<EventGoal> getTeamGoals(Team team) {
        return this.goals.get(team);
    }

    /**
     * This function returns a list of EventInterrupt objects
     * 
     * @return The list of interrupts.
     */
    public List<EventInterrupt> getInterrupts() {
        return this.interrupts;
    }

}