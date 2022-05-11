package com.example.scoredei.controllers;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.User;
import com.example.scoredei.data.events.*;
import com.example.scoredei.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "redirect:/";
    }

    @GetMapping("/add-team")
    public String addTeam(Model model) {
        model.addAttribute("team", new Team());
        return "add-team";
    }

    @PostMapping("/create-team")
    public String createTeam(@ModelAttribute Team team) {
        this.teamService.addTeam(team);
        return "redirect:/admin/teams";
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
        return "redirect:/admin/games";
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
        return "redirect:/admin/players";
    }

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

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", this.userService.getUsers());
        return "users";
    }

    @GetMapping("/events")
    public String getEvents(Model model){
        model.addAttribute("events", this.eventService.getEvents());
        return "events";
    }

    @GetMapping("/redirect-game-end")
    public String redirectGameEnd(Model model){
        return "redirect:/admin/add-game-end";
    }

    @GetMapping("/redirect-game-goal")
    public String redirectGameGoal(Model model){
        return "redirect:/admin/add-game-start";
    }

    @GetMapping("/redirect-game-interrupt")
    public String redirectGameInterrupt(Model model){
        return "redirect:/admin/add-game-interrupt";
    }

    @GetMapping("/redirect-game-red-card")
    public String redirectGameRedCard(Model model){
        return "redirect:/admin/add-game-red-card";
    }

    @GetMapping("/redirect-game-resume")
    public String redirectGameResume(Model model){
        return "redirect:/admin/add-game-resume";
    }

    @GetMapping("/redirect-game-start")
    public String redirectGameStart(Model model){
        return "redirect:/admin/add-game-start";
    }

    @GetMapping("/redirect-game-yellow-card")
    public String redirectGameYellowCard(Model model){
        return "redirect:/admin/add-game-yellow-card";
    }

    @GetMapping("/add-event")
    public String addEvent(Model model){
        return "add-event";
    }

    @GetMapping("/add-game-start")
    public String addGameStart(Model model){
        model.addAttribute("event", new EventStart());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-start";
    }

    @PostMapping("/create-game-start")
    public String createGameStart(@ModelAttribute EventStart event){
        this.eventService.addEvent(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/add-game-end")
    public String addGameEnd(Model model){
        model.addAttribute("event", new EventEnd());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-end";
    }

    @PostMapping("/create-game-end")
    public String createGameEnd(@ModelAttribute EventEnd event){
        this.eventService.addEvent(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/add-game-goal")
    public String addGameGoal(Model model){
        model.addAttribute("event", new EventGoal());
        model.addAttribute("players", this.playerService.getPlayers());
        model.addAttribute("teams", this.teamService.getTeams());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-goal";
    }

    @PostMapping("/create-game-goal")
    public String createGameGoal(@ModelAttribute EventGoal event){
        this.eventService.addEvent(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/add-game-interrupt")
    public String addGameInterrupt(Model model){
        model.addAttribute("event", new EventInterrupt());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-interrupt";
    }

    @PostMapping("/create-game-interrupt")
    public String createGameInterrupt(@ModelAttribute EventInterrupt event){
        this.eventService.addEvent(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/add-game-red-card")
    public String addGameRedCard(Model model){
        model.addAttribute("event", new EventRedCard());
        model.addAttribute("players", this.playerService.getPlayers());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-red-card";
    }

    @PostMapping("/create-game-red-card")
    public String createGameRedCard(@ModelAttribute EventRedCard event){
        this.eventService.addEvent(event);       
        return "redirect:/admin/events";
    }

    @GetMapping("/add-game-resume")
    public String addGameResume(Model model){
        model.addAttribute("event", new EventResume());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-resume";
    }

    @PostMapping("/create-game-resume")
    public String createGameResume(@ModelAttribute EventResume event){
        this.eventService.addEvent(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/add-game-yellow-card")
    public String addGameYellowcard(Model model){
        model.addAttribute("event", new EventYellowCard());
        model.addAttribute("players", this.playerService.getPlayers());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-yellow-card";
    }

    @PostMapping("/create-game-yellow-card")
    public String createGameYellowCard(@ModelAttribute EventYellowCard event){
        this.eventService.addEvent(event);
        return "redirect:/admin/events";
    }

    
}
