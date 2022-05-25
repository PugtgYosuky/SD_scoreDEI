package com.example.scoredei.controllers;

import java.util.Optional;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.User;
import com.example.scoredei.data.forms.GameForm;
import com.example.scoredei.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    // *********************** ADD METHODS ***********************

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
        model.addAttribute("gameForm", new GameForm());
        model.addAttribute("teams", this.teamService.getTeams());
        return "add-game";
    }

    @PostMapping("/create-game")
    public String createGame(@ModelAttribute GameForm gameForm) {
        gameForm.getGame().addTeam(gameForm.getTeamA());
        gameForm.getGame().addTeam(gameForm.getTeamB());
        this.gameService.addGame(gameForm.getGame());
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

    // *********************** GET USERS ***********************

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", this.userService.getUsers());
        return "users";
    }

    @GetMapping("/user")
    public String getUser(@RequestParam("username") String username, Model model) {
        Optional<User> user = this.userService.getUser(username);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user";
        } else {
            model.addAttribute("prev", "/admin/users");
            model.addAttribute("message", "User not found");
            return "404";
        }
    }

    // *********************** EDIT METHODS ***********************

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

    @GetMapping ("/edit-player")
    public String editPlayer(Model model, @RequestParam(name="id", required=true) int id){
        Optional<Player> player = this.playerService.getPlayer(id);
        if(player.isPresent()) {
            model.addAttribute("player", player.get());
            model.addAttribute("teams", this.teamService.getTeams());
            return "edit-player";
        } else {
            return "redirect:/players";
        }
    }

    @PostMapping("/save-edit-player")
    public String saveEditPlayer(@ModelAttribute Player player) {
        this.playerService.addPlayer(player);
        return "redirect:/players";
    }

    @GetMapping("/edit-game")
    public String editGame(@RequestParam(name="id", required=true) int id, Model model) {
        Optional<Game> game = this.gameService.getGame(id);
        if(game.isPresent()) {
            model.addAttribute("gameForm", new GameForm(game.get()));
            model.addAttribute("teams", this.teamService.getTeams());
            return "edit-game";
        } else {
            return "redirect:/games";
        }
    }

    @PostMapping("/save-edit-game")
    public String saveEditGame(@ModelAttribute GameForm gameForm) {
        gameForm.getGame().setTeamA(gameForm.getTeamA());
        gameForm.getGame().setTeamB(gameForm.getTeamB());
        this.gameService.addGame(gameForm.getGame());
        return "redirect:/games";
    }

    @GetMapping("/edit-user")
    public String editUser(@RequestParam(name="id", required=true) int id, Model model){
        Optional<User> user = this.userService.getUser(id);
        if(user.isPresent()) {
            model.addAttribute("user", user.get());
            return "edit-user";
        }
        model.addAttribute("prev", "/admin/users");
        model.addAttribute("message", "User not found");
        return "404";
    }

    @PostMapping("/save-edit-user")
    public String saveEditUser(@ModelAttribute User user) {
        this.userService.addUser(user);
        return "redirect:/admin/users";
    }

    // *********************** DELETE METHODS ***********************

    @PostMapping("/delete-game")
    public String deleteGame(@RequestParam(name="id", required=true) int id, Model model) {
        if(this.gameService.deleteGame(id))
            return "redirect:/games";
        model.addAttribute("prev", "/games");
        model.addAttribute("message", "Game not found");
        return "404";
    }

    @PostMapping("/delete-player")
    public String deletePlayer(@RequestParam(name="id", required=true) int id, Model model) {
        if(this.playerService.deletePlayer(id))
            return "redirect:/players";
        model.addAttribute("prev", "/players");
        model.addAttribute("message", "Player not found");
        return "404";
    }

    @PostMapping("/delete-team")
    public String deleteTeam(@RequestParam(name="id", required=true) int id, Model model) {
        if(this.teamService.deleteTeam(id))
            return "redirect:/teams";
        model.addAttribute("prev", "/teams");
        model.addAttribute("message", "Team not found");
        return "404";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam(name="id", required=true) int id, Model model) {
        if(this.userService.deleteUser(id))
            return "redirect:/admin/users";
        model.addAttribute("prev", "/admin/users");
        model.addAttribute("message", "User not found");
        return "404";

    }
}
