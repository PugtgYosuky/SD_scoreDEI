package com.example.scoredei.controllers;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.statistics.GameStatistics;
import com.example.scoredei.data.statistics.GeneralStatistics;
import com.example.scoredei.services.EventService;
import com.example.scoredei.services.GameService;
import com.example.scoredei.services.PlayerService;
import com.example.scoredei.services.TeamService;
import com.example.scoredei.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class GeneralController {

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;

    @Autowired
    EventService eventService;

    @GetMapping("/teams")
    public String getTeams(Model model) {
        model.addAttribute("teams", this.teamService.getTeams());
        return "teams";
    }

    @GetMapping("/games")
    public String getGames(Model model) {
        model.addAttribute("games", this.gameService.getGames());
        return "games";
    }

    @GetMapping("/players")
    public String getPlayers(Model model) {
        model.addAttribute("players", this.playerService.getPlayers());
        return "players";
    }

    @GetMapping("/events")
    public String getEvents(Model model){
        model.addAttribute("events", this.eventService.getEvents());
        return "events";
    }

    @GetMapping("/")
    public String homePage(Model model) {
        return "redirect:/games";
    }

    //TODO: create endpoints to see a especific team, game, player, user, event

    @GetMapping("/game-statistics")
    public String getGameStatistics(@RequestParam(name="id", required=true) int id, Model model) {
        // TODO: REVER
        Optional<Game> g = this.gameService.getGame(id);
        if(g.isPresent()) {
            Game game = g.get();
            GameStatistics gameStatistics = new GameStatistics(game);
            model.addAttribute("teamAGoals", gameStatistics.getTeamGoals(game.getTeamA()));
            model.addAttribute("teamBGoals", gameStatistics.getTeamGoals(game.getTeamB()));
            model.addAttribute("teamAYellowCards", gameStatistics.getTeamYellowCards(game.getTeamA()));
            model.addAttribute("teamBYellowCards", gameStatistics.getTeamYellowCards(game.getTeamB()));
            model.addAttribute("teamARedCards", gameStatistics.getTeamRedCards(game.getTeamA()));
            model.addAttribute("teamBRedCards", gameStatistics.getTeamRedCards(game.getTeamB()));
            model.addAttribute("interrupts", gameStatistics.getInterrupts());
            model.addAttribute("game", game);
            return "game-statistics";
        }
        return "redirect:/games";
    }

    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        GeneralStatistics generalStatistics = new GeneralStatistics(gameService, teamService);

        List<Team> teams = this.teamService.getTeams();
        teams.sort(Comparator.comparingInt(generalStatistics::getTeamPoints));

        model.addAttribute("teams", teams);
        model.addAttribute("gameStatistics", generalStatistics);

        return "statistics";
    }
    
}
