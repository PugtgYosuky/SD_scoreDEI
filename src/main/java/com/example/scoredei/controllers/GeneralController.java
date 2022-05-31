package com.example.scoredei.controllers;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.User;

import com.example.scoredei.data.forms.EventForm;
import com.example.scoredei.data.forms.LoginForm;
import com.example.scoredei.data.statistics.GameStatistics;
import com.example.scoredei.data.statistics.GeneralStatistics;
import com.example.scoredei.services.GameService;
import com.example.scoredei.services.PlayerService;
import com.example.scoredei.services.TeamService;
import com.example.scoredei.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * The class is a controller class that handles the requests from the user and returns the appropriate
 * view.
 */
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


    /**
     * This function gets all the teams from the database, sorts them by points, and then returns the
     * teams page with the teams and general statistics.
     * 
     * @param model The model is a Map of object names (the keys) to objects (the values). The model is
     * passed to the view by the controller.
     * @return A string that is the name of the html file.
     */
    @GetMapping("/teams")
    public String getTeams(Model model) {
        GeneralStatistics generalStatistics = new GeneralStatistics(gameService, teamService);

        List<Team> teams = this.teamService.getTeams();
        teams.sort(Comparator.comparingInt(generalStatistics::getTeamPoints).reversed());

        model.addAttribute("teams", teams);
        model.addAttribute("generalStatistics", generalStatistics);

        return "teams";
    }

   /**
    * This function returns a list of games from the database
    * 
    * @param model This is the model that will be passed to the view.
    * @return A string that is the name of the html file.
    */
    @GetMapping("/games")
    public String getGames(Model model) {
        model.addAttribute("games", this.gameService.getGames());
        return "games";
    }

    /**
     * It gets all the players from the database and puts them in a list.
     * 
     * @param model The model is an object that holds the data that you want to pass to the view.
     * @return A string that is the name of the html file.
     */
    @GetMapping("/players")
    public String getPlayers(Model model) {
        GeneralStatistics generalStatistics = new GeneralStatistics(gameService, teamService);

        model.addAttribute("generalStatistics", generalStatistics);
        model.addAttribute("players", this.playerService.getPlayers());
        
        return "players";
    }

    /**
     * This function redirects the user to the games page
     * 
     * @param model The model is an object that holds the data that you want to pass to the view.
     * @return A redirect to the games page.
     */
    @GetMapping("/")
    public String homePage(Model model) {
        return "redirect:/games";
    }

    /**
     * It gets a game from the database, and then adds the game, the teams, the players, and the event
     * form to the model
     * 
     * @param id the id of the game
     * @param model Model
     * @return A string that is the name of the html file.
     */
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

    /**
     * This function takes in a team id, and if the team exists, it returns the team page, otherwise it
     * returns a 404 page
     * 
     * @param id the id of the team to be displayed
     * @param model The model is an object that holds data that you want to pass to the view.
     * @return A string that is the name of the html file.
     */
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

    /**
     * If the player is found, return the player page with it's ingormation, otherwise return the 404 page
     * 
     * @param id the id of the player to be retrieved
     * @param model The model is an object that holds data that you want to pass to the view.
     * @return A String
     */
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

    /**
     * This function takes in a game id, and returns a page with the game statistics for that game
     * 
     * @param id the id of the game
     * @param model The model is an object that allows you to store data that you can use in the view.
     * @return A game-statistics page
     */
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

    /**
     * If there are no users in the database, add a user with the username "admin" and the password
     * "admin"
     * 
     * @param model The model is a Map of model objects, which can be used to pass data to the view.
     * @return A string
     */
    @GetMapping("/login")
    public String login(Model model) {
        if(userService.getUser("admin").isEmpty() && userService.getUsers().size() == 0) {
            userService.addUser(new User("admin", "admin", "admin", "admin@admin.com", "911111111", true));
        }
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    /**
     * If the user is not null, then set the user attribute in the session to the user object
     * 
     * @param loginForm The form that the user filled out on the login page.
     * @param model The model is a map of data that is used for rendering the view.
     * @return A String
     */
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

    /**
     * This function is called when the user clicks the logout button. It clears the session and
     * redirects the user to the login page.
     * 
     * @param model The model is a Map that is used to store the data that will be displayed on the
     * view page.
     * @return A redirect to the root of the application.
     */
    @GetMapping("/logout")
    public String logout(Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("user", null);
        model.addAttribute("loginForm", new LoginForm());
        return "redirect:/";
    }
    
}
