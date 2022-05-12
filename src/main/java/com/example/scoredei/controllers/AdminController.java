package com.example.scoredei.controllers;

import java.util.Optional;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.User;
import com.example.scoredei.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
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

    @GetMapping("/add-user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/create-user")
    public String createUser(@ModelAttribute User user) {
        this.userService.addUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/add-team")
    public String addTeam(Model model) {
        model.addAttribute("team", new Team());
        return "add-team";
    }

    @PostMapping("/create-team")
    public String createTeam(@ModelAttribute Team team) {
        this.teamService.addTeam(team);
        return "redirect:/teams";
    }

    @GetMapping("/add-game")
    public String addGame(Model model) {
        model.addAttribute("game", new Game());
        model.addAttribute("teams", this.teamService.getTeams());
        return "add-game";
    }

    @PostMapping("/create-game")
    public String createGame(@ModelAttribute Game game) {
        this.gameService.addGame(game);
        return "redirect:/games";
    }

    @GetMapping("/add-player")
    public String addPlayer(Model model) {
        model.addAttribute("player", new Player());
        model.addAttribute("teams", this.teamService.getTeams());
        return "add-player";
    }

    @PostMapping("/create-player")
    public String createPlayer(@ModelAttribute Player player) {
        this.playerService.addPlayer(player);
        return "redirect:/players";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", this.userService.getUsers());
        return "users";
    }

    @GetMapping("/edit-team")
    public String editTeam(Model model, @RequestParam(name="id", required=true) int id) {
        Optional<Team> team = this.teamService.getTeam(id);
        if(team.isPresent()) {
            model.addAttribute("team", team.get());
            return "edit-team";
        } else {
            return "redirect:/teams";
        } 
    }

    @PostMapping("/save-edit-team")
    public String saveEditTeam(@ModelAttribute Team team) {
        this.teamService.addTeam(team);
        return "redirect:/teams";
    }

}
