package com.example.scoredei.controllers;

import com.example.scoredei.services.EventService;
import com.example.scoredei.services.GameService;
import com.example.scoredei.services.PlayerService;
import com.example.scoredei.services.TeamService;
import com.example.scoredei.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


    
}
