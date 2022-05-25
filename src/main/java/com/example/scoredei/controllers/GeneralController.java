package com.example.scoredei.controllers;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
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

    @GetMapping("/game")
    public String getGame(@RequestParam(name="id", required=true) int id, Model model) {
        Optional<Game> g = this.gameService.getGame(id);
        if(g.isPresent()){
            model.addAttribute("game", g.get());
            model.addAttribute("gameStatistics", new GameStatistics(g.get()));
            return "game";
        }
        model.addAttribute("prev", "/games");
        model.addAttribute("message", "Game not found");
        return "404";
    }

    @GetMapping("/team")
    public String getTeam(@RequestParam(name="id", required=true) int id, Model model) {
        Optional<Team> t = this.teamService.getTeam(id);
        if(t.isPresent()){
            model.addAttribute("team", t.get());
            return "team";
        }
        model.addAttribute("prev", "/teams");
        model.addAttribute("message", "Team not found");
        return "404";
    }

    @GetMapping("/player")
    public String getPlayer(@RequestParam(name="id", required=true) int id, Model model) {
        Optional<Player> player = this.playerService.getPlayer(id);
        if(player.isPresent()){
            model.addAttribute("player", player.get());
            return "player";
        }
        model.addAttribute("prev", "/players");
        model.addAttribute("message", "Player not found");
        return "404";
    }

    @GetMapping("/game-statistics")
    public String getGameStatistics(@RequestParam(name="id", required=true) int id, Model model) {
        Optional<Game> g = this.gameService.getGame(id);
        if(g.isPresent()) {
            Game game = g.get();
            GameStatistics gameStatistics = new GameStatistics(game);
            model.addAttribute("gameStatistics", gameStatistics);
            model.addAttribute("game", game);
            return "game-statistics";
        }
        return "redirect:/games";
    }

    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        GeneralStatistics generalStatistics = new GeneralStatistics(gameService, teamService);

        List<Team> teams = this.teamService.getTeams();
        teams.sort(Comparator.comparingInt(generalStatistics::getTeamPoints).reversed());

        model.addAttribute("teams", teams);
        model.addAttribute("generalStatistics", generalStatistics);
        model.addAttribute("bestScorer", )

        //TODO: ver o melhor marcador do campeonato :)

        return "statistics";
    }
    
}
