package com.example.scoredei.controllers;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.events.EventEnd;
import com.example.scoredei.data.events.EventGoal;
import com.example.scoredei.data.events.EventInterrupt;
import com.example.scoredei.data.events.EventRedCard;
import com.example.scoredei.data.events.EventResume;
import com.example.scoredei.data.events.EventStart;
import com.example.scoredei.data.events.EventYellowCard;
import com.example.scoredei.data.filters.AuthFilter;
import com.example.scoredei.data.forms.EventForm;
import com.example.scoredei.data.types.EventType;
import com.example.scoredei.services.EventService;
import com.example.scoredei.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Bean
    public FilterRegistrationBean<AuthFilter> loggingFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthFilter(userService));
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }

    @PostMapping("/add-game-start")
    public String createGameStart(@ModelAttribute EventForm eventForm, Model model){
        Game game = eventForm.getGame();
        if(eventForm.getGame().getEvents().size() != 0) {
            model.addAttribute("message", "The game has already started!");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(new Date().compareTo(game.getStart()) < 0) {
            model.addAttribute("message", "It's too soon to start the game!");
            model.addAttribute("prev", "/game?id=" + game.getId());
            return "invalid";
        }
        EventStart event = new EventStart(eventForm.getGame(), new Date());
        this.eventService.addEvent(event);
        return "redirect:/game?id=" + game.getId();
    }

    @PostMapping("/add-game-end")
    public String createGameEnd(@ModelAttribute EventForm eventForm, Model model){
        if(eventForm.getGame().getEvents().size() == 0) {
            model.addAttribute("message", "The game hasn't started yet");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.END) {
            model.addAttribute("message", "The game has ended");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.INTERRUPT) {
            model.addAttribute("message", "The game has been interrupted");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        EventEnd event = new EventEnd(eventForm.getGame(), new Date());
        long timeDiff = event.getTime().getTime() - eventForm.getGame().getStart().getTime();
        long seconds = timeDiff / 1000;

        long minGameLength = 0;

        try {
            BufferedReader input = new BufferedReader(new FileReader(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("game.minLength")).toURI())));
            minGameLength = Long.parseLong(input.readLine());
        } catch(Exception exc) {
            exc.printStackTrace();
        }

        if(seconds < minGameLength) {
            model.addAttribute("message", "The game is too short!");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        // TODO: confirmar que já passara pelo menos 90 min e que o jogo não tinha terminado
        this.eventService.addEvent(event);
        return "redirect:/game?id=" + eventForm.getGame().getId();
    }

    @PostMapping("/add-game-goal")
    public String createGameGoal(@ModelAttribute EventForm eventForm, Model model){
        if(eventForm.getGame().getEvents().size() == 0) {
            model.addAttribute("message", "The game hasn't started yet");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.END) {
            model.addAttribute("message", "The game has ended");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.INTERRUPT) {
            model.addAttribute("message", "The game has been interrupted");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().hasRedCards(eventForm.getPlayer())) {
            model.addAttribute("message", "This player has a red card");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        EventGoal event = new EventGoal(eventForm.getGame(), new Date(), eventForm.getPlayer(), eventForm.getTeam());
        this.eventService.addEvent(event);
        return "redirect:/game?id=" + eventForm.getGame().getId();
    }

    @PostMapping("/add-game-interrupt")
    public String createGameInterrupt(@ModelAttribute EventForm eventForm, Model model){
        if(eventForm.getGame().getEvents().size() == 0) {
            model.addAttribute("message", "The game hasn't started yet");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.END) {
            model.addAttribute("message", "The game has ended");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.INTERRUPT) {
            model.addAttribute("message", "The game has been interrupted");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        EventInterrupt event = new EventInterrupt(eventForm.getGame(), new Date());
        this.eventService.addEvent(event);
        return "redirect:/game?id=" + eventForm.getGame().getId();
    }

    @PostMapping("/add-game-red-card")
    public String createGameRedCard(@ModelAttribute EventForm eventForm, Model model){
        if(eventForm.getGame().getEvents().size() == 0) {
            model.addAttribute("message", "The game hasn't started yet");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.END) {
            model.addAttribute("message", "The game has ended");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.INTERRUPT) {
            model.addAttribute("message", "The game has been interrupted");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().hasRedCards(eventForm.getPlayer())) {
            model.addAttribute("message", "This player has a red card");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        EventRedCard event = new EventRedCard(eventForm.getGame(), new Date(), eventForm.getPlayer());
        this.eventService.addEvent(event);       
        return "redirect:/game?id=" + eventForm.getGame().getId();
    }

    @PostMapping("/add-game-resume")
    public String createGameResume(@ModelAttribute EventForm eventForm, Model model){
        if(eventForm.getGame().getEvents().size() == 0) {
            model.addAttribute("message", "The game hasn't started yet");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.END) {
            model.addAttribute("message", "The game has ended");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.INTERRUPT) {
            model.addAttribute("message", "The game has been interrupted");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        EventResume event = new EventResume(eventForm.getGame(), new Date());
        this.eventService.addEvent(event);
        return "redirect:/game?id=" + eventForm.getGame().getId();
    }

    @PostMapping("/add-game-yellow-card")
    public String createGameYellowCard(@ModelAttribute EventForm eventForm, Model model){
        if(eventForm.getGame().getEvents().size() == 0) {
            model.addAttribute("message", "The game hasn't started yet");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.END) {
            model.addAttribute("message", "The game has ended");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().getLastEvent().getType() == EventType.INTERRUPT) {
            model.addAttribute("message", "The game has been interrupted");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        if(eventForm.getGame().hasRedCards(eventForm.getPlayer())) {
            model.addAttribute("message", "This player has a red card");
            model.addAttribute("prev", "/game?id=" + eventForm.getGame().getId());
            return "invalid";
        }
        EventYellowCard event = new EventYellowCard(eventForm.getGame(), new Date(), eventForm.getPlayer());
        this.eventService.addEvent(event);
        return "redirect:/game?id=" + eventForm.getGame().getId();
    }

}
