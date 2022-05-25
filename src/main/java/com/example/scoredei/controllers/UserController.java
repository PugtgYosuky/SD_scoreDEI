package com.example.scoredei.controllers;

import com.example.scoredei.data.events.EventEnd;
import com.example.scoredei.data.events.EventGoal;
import com.example.scoredei.data.events.EventInterrupt;
import com.example.scoredei.data.events.EventRedCard;
import com.example.scoredei.data.events.EventResume;
import com.example.scoredei.data.events.EventStart;
import com.example.scoredei.data.events.EventYellowCard;
import com.example.scoredei.data.types.EventType;
import com.example.scoredei.services.EventService;
import com.example.scoredei.services.GameService;
import com.example.scoredei.services.PlayerService;
import com.example.scoredei.services.TeamService;
import com.example.scoredei.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

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

    @GetMapping("/redirect-game-end")
    public String redirectGameEnd(Model model){
        return "redirect:/add-game-end";
    }

    @GetMapping("/redirect-game-goal")
    public String redirectGameGoal(Model model){
        return "redirect:/add-game-goal";
    }

    @GetMapping("/redirect-game-interrupt")
    public String redirectGameInterrupt(Model model){
        return "redirect:/add-game-interrupt";
    }

    @GetMapping("/redirect-game-red-card")
    public String redirectGameRedCard(Model model){
        return "redirect:/add-game-red-card";
    }

    @GetMapping("/redirect-game-resume")
    public String redirectGameResume(Model model){
        return "redirect:/add-game-resume";
    }

    @GetMapping("/redirect-game-start")
    public String redirectGameStart(Model model){
        return "redirect:/add-game-start";
    }

    @GetMapping("/redirect-game-yellow-card")
    public String redirectGameYellowCard(Model model){
        return "redirect:/add-game-yellow-card";
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
    public String createGameStart(@ModelAttribute EventStart event, Model model){
        if(event.getGame().getEvents().size() != 0) {
            model.addAttribute("prev", "/game?id=" + event.getGame().getId());
            return "invalid-event";
        }
        this.eventService.addEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/add-game-end")
    public String addGameEnd(Model model){
        model.addAttribute("event", new EventEnd());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-end";
    }

    @PostMapping("/create-game-end")
    public String createGameEnd(@ModelAttribute EventEnd event, Model model){

        if( event.getGame().getEvents().size() == 0 ||
            event.getGame().getLastEvent().getType() == EventType.END ||
            event.getGame().getLastEvent().getType() == EventType.INTERRUPT) {
            model.addAttribute("prev", "/game?id=" + event.getGame().getId());
            return "invalid-event";    
        }
        // confirmar que já passara pelo menos 90 min e que o jogo não tinha terminado
        this.eventService.addEvent(event);
        return "redirect:/events";
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
    public String createGameGoal(@ModelAttribute EventGoal event, Model model){
        this.eventService.addEvent(event);
        if(event.getGame().getEvents().size() == 0 ||
            event.getGame().getLastEvent().getType() == EventType.END ||
            event.getGame().getLastEvent().getType() == EventType.INTERRUPT ||
            event.getGame().hasRedCards(event.getPlayer())) {
            model.addAttribute("prev", "/game?id=" + event.getGame().getId());
            return "invalid-event";
        }
        return "redirect:/events";
    }

    @GetMapping("/add-game-interrupt")
    public String addGameInterrupt(Model model){
        model.addAttribute("event", new EventInterrupt());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-interrupt";
    }

    @PostMapping("/create-game-interrupt")
    public String createGameInterrupt(@ModelAttribute EventInterrupt event, Model model){
        if(event.getGame().getEvents().size() == 0 ||
            event.getGame().getLastEvent().getType() == EventType.END ||
            event.getGame().getLastEvent().getType() == EventType.INTERRUPT){
            model.addAttribute("prev", "/game?id=" + event.getGame().getId());
            return "invalid-event";
        }
            
        this.eventService.addEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/add-game-red-card")
    public String addGameRedCard(Model model){
        model.addAttribute("event", new EventRedCard());
        model.addAttribute("players", this.playerService.getPlayers());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-red-card";
    }

    @PostMapping("/create-game-red-card")
    public String createGameRedCard(@ModelAttribute EventRedCard event, Model model){
        if(event.getGame().getEvents().size() == 0 ||
            event.getGame().getLastEvent().getType() == EventType.END ||
            event.getGame().getLastEvent().getType() == EventType.INTERRUPT ||
            event.getGame().hasRedCards(event.getPlayer())) {
            model.addAttribute("prev", "/game?id=" + event.getGame().getId());
            return "invalid-event";
        }
        this.eventService.addEvent(event);       
        return "redirect:/events";
    }

    @GetMapping("/add-game-resume")
    public String addGameResume(Model model){
        model.addAttribute("event", new EventResume());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-resume";
    }

    @PostMapping("/create-game-resume")
    public String createGameResume(@ModelAttribute EventResume event, Model model){
        if(event.getGame().getEvents().size() == 0 ||
            event.getGame().getLastEvent().getType() == EventType.END ||
            event.getGame().getLastEvent().getType() != EventType.INTERRUPT) {
            model.addAttribute("prev", "/game?id=" + event.getGame().getId());
            return "invalid-event";
        }
        this.eventService.addEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/add-game-yellow-card")
    public String addGameYellowcard(Model model){
        model.addAttribute("event", new EventYellowCard());
        model.addAttribute("players", this.playerService.getPlayers());
        model.addAttribute("games", this.gameService.getGames());
        return "add-game-yellow-card";
    }

    @PostMapping("/create-game-yellow-card")
    public String createGameYellowCard(@ModelAttribute EventYellowCard event, Model model){
        if(event.getGame().getEvents().size() == 0 ||
            event.getGame().getLastEvent().getType() == EventType.END ||
            event.getGame().getLastEvent().getType() == EventType.INTERRUPT ||
            event.getGame().hasRedCards(event.getPlayer())) {
            model.addAttribute("prev", "/game?id=" + event.getGame().getId());
            return "invalid-event";
        }
        this.eventService.addEvent(event);
        return "redirect:/events";
    }

}
