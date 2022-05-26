package com.example.scoredei.controllers;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.User;
import com.example.scoredei.data.filters.AdminFilter;
import com.example.scoredei.data.forms.EventForm;
import com.example.scoredei.data.forms.LoginForm;
import com.example.scoredei.data.statistics.GameStatistics;
import com.example.scoredei.data.statistics.GeneralStatistics;
import com.example.scoredei.services.EventService;
import com.example.scoredei.services.GameService;
import com.example.scoredei.services.PlayerService;
import com.example.scoredei.services.TeamService;
import com.example.scoredei.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
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

    @Bean
    public FilterRegistrationBean<AdminFilter> loggingFilter(){
        FilterRegistrationBean<AdminFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/admin/*");

        registrationBean.setOrder(1);

        return registrationBean;
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

    @GetMapping("/")
    public String homePage(Model model) {
        return "redirect:/games";
    }

    @GetMapping("/game")
    public String getGame(@RequestParam(name="id", required=true) int id, Model model) {
        Optional<Game> g = this.gameService.getGame(id);
        if(g.isPresent()){
            Game game = g.get();
            model.addAttribute("game", game);

            List<Player> players = game.getTeamA().getPlayers();
            players.addAll(game.getTeamB().getPlayers());

            model.addAttribute("teams", game.getTeams());
            model.addAttribute("players", players);
            model.addAttribute("eventForm", new EventForm(game));
            model.addAttribute("gameStatistics", new GameStatistics(game));
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
        //TODO: ver o melhor marcador do campeonato :)

        return "statistics";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("user", null);
        model.addAttribute("loginForm", new LoginForm());
        return "redirect:/";
    }

    @PostMapping("/authenticate")
    public String authenticate(LoginForm loginForm, Model model) {
        User user = this.userService.authenticate(loginForm.getUsername(), loginForm.getPassword());
        
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        if(user == null){
            session.setAttribute("user", null);
            return "invalid-login";
        }

        session.setAttribute("user", user);
        return "redirect:/";
    }
    
}
