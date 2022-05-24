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
    private Map<Team, List<EventYellowCard>> yellowCards;
    private Map<Team, List<EventRedCard>> redCards;
    private Map<Team, List<EventGoal>> goals;
    private List<EventInterrupt> interrupts;

    public GameStatistics(Game game) {
        this.yellowCards = new HashMap<>();
        this.redCards = new HashMap<>();
        this.goals = new HashMap<>();
        this.interrupts = new ArrayList<>();
        this.game = game;
        this.process();
    }

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
            switch(e.getType()) {
                case GOAL:
                    goal = (EventGoal) e;
                    this.goals.get(goal.getTeam()).add(goal);
                    break;
                case INTERRUPT:
                    interrupt = (EventInterrupt) e;
                    this.interrupts.add(interrupt);
                    break;
                case RED_CARD:
                    redCard = (EventRedCard) e;
                    this.redCards.get(redCard.getPlayer().getTeam()).add(redCard);
                    break;
                case YELLOW_CARD:
                    yellowCard = (EventYellowCard) e;
                    this.yellowCards.get(yellowCard.getPlayer().getTeam()).add(yellowCard);
                    break;
                default:
                    break;
            }
        }
    }

    public Game getGame() {
        return this.game;
    }

    public List<EventRedCard> getTeamRedCards(Team team) {
        return this.redCards.get(team);
    }

    public List<EventYellowCard> getTeamYellowCards(Team team) {
        return this.yellowCards.get(team);
    }

    public List<EventGoal> getTeamGoals(Team team) {
        return this.goals.get(team);
    }

    public List<EventInterrupt> getInterrupts() {
        return this.interrupts;
    }

}