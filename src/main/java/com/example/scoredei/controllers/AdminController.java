package com.example.scoredei.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.scoredei.data.Game;
import com.example.scoredei.data.Player;
import com.example.scoredei.data.Team;
import com.example.scoredei.data.User;
import com.example.scoredei.data.events.*;
import com.example.scoredei.data.filters.AdminFilter;
import com.example.scoredei.data.forms.GameForm;
import com.example.scoredei.data.types.EventType;
import com.example.scoredei.data.types.PlayerType;
import com.example.scoredei.services.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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
    EventService eventService;

    @Autowired
    PlayerService playerService;

    // *********************** FILTER ***********************

    @Bean
    public FilterRegistrationBean<AdminFilter> adminLoggingFilter(){
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter(userService));
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    // *********************** ADD METHODS ***********************

    @GetMapping("/add-user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/create-user")
    public String createUser(@ModelAttribute User user) {
        // user.setPassword(user.getPassword());
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


    //************************ POPULATE DATABASE ************************/
    
    private void addPlayers(Team team, int teamID, String key) {

        try {
            URL url = new URL("https://v3.football.api-sports.io/players?team=" + teamID + "&season=2020");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("x-rapidapi-host", "v3.football.api-sports.io");
            connection.setRequestProperty("x-rapidapi-key", key);
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = bufferedReader.readLine();
    
            connection.disconnect();

            JSONObject response = new JSONObject(line);
            JSONArray players = response.getJSONArray("response");

            for(int i = 0; i < players.length(); i++){
                JSONObject playerObject = players.getJSONObject(i).getJSONObject("player");
                JSONObject birth = playerObject.getJSONObject("birth");
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birth.getString("date"));
                PlayerType position = PlayerType.values()[new Random().nextInt(PlayerType.values().length)];
                Player player = new Player(playerObject.getString("name"), date, position, team, playerObject.getString("photo"));
                this.playerService.addPlayer(player);
            }
        }catch(Exception exc) {
            exc.printStackTrace();
        }
    }

    @PostMapping("/populate-database")
    public String populateDatabase() {

        String key = "";
        try {
            BufferedReader input = new BufferedReader(new FileReader(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("api.key")).toURI())));
            key = input.readLine();
        } catch(Exception exc) {
            exc.printStackTrace();
        }

        try {
            URL url = new URL("https://v3.football.api-sports.io/teams?country=Portugal");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("x-rapidapi-host", "v3.football.api-sports.io");
            connection.setRequestProperty("x-rapidapi-key", key);
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = bufferedReader.readLine();
    
            connection.disconnect();
            System.out.println(line);

            JSONObject response = new JSONObject(line);
            JSONArray teams = response.getJSONArray("response");

            for(int i = 1; i < 9; i++){
                JSONObject teamObject = teams.getJSONObject(i).getJSONObject("team");
                Team team = new Team(teamObject.getString("name"), teamObject.getString("logo"));
                this.teamService.addTeam(team);
                addPlayers(team, teamObject.getInt("id"), key);
            }

        } catch(Exception exc) {
            exc.printStackTrace();
        }

            
        return "redirect:/games";
    }

    @PostMapping("/populate-games")
    public String populateGames() {
        List<Team> allTeams = this.teamService.getTeams();
        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            int a = random.nextInt(allTeams.size()), b = random.nextInt(allTeams.size());
            Team aa = allTeams.get(a), bb = allTeams.get(b);
            if(a == b)
                continue;
            Date date = new Date();
            Game game = new Game(aa, bb, "Estádio aleatório", date);
            List<Player> players = new ArrayList<>(aa.getPlayers());
            players.addAll(bb.getPlayers());
            gameService.addGame(game);

            Date after = new Date(date.getTime() + 60 * 1000);
            EventStart start = new EventStart(game, after);
            eventService.addEvent(start);
            game.addEvent(start);
            for(int j = 0; j < 10; j++) {
                after = new Date(after.getTime() + 60 * 1000);
                int rndEvent = random.nextInt(4);
                if(game.getLastEvent().getType().equals(EventType.INTERRUPT)) {
                    EventResume resume = new EventResume(game, after);
                    eventService.addEvent(resume);
                    game.addEvent(resume);
                    continue;
                }

                Player rndPlayer = players.get(random.nextInt(players.size()));
                switch (rndEvent) {
                    case 0 -> {
                        EventGoal goal = new EventGoal(game, after, players.get(random.nextInt(players.size())), random.nextInt(2) == 0 ? aa : bb);
                        eventService.addEvent(goal);
                        game.addEvent(goal);
                    }
                    case 1 -> {
                        EventInterrupt interrupt = new EventInterrupt(game, after);
                        eventService.addEvent(interrupt);
                        game.addEvent(interrupt);
                    }
                    case 2 -> {
                        if(!game.hasRedCards(rndPlayer)) {
                            EventRedCard redCard = new EventRedCard(game, after, rndPlayer);
                            eventService.addEvent(redCard);
                            game.addEvent(redCard);
                        }
                    }
                    case 3 -> {
                        EventYellowCard yellowCard = new EventYellowCard(game, after, rndPlayer);
                        eventService.addEvent(yellowCard);
                        game.addEvent(yellowCard);
                    }
                }
            }
            if(game.getLastEvent().getType().equals(EventType.INTERRUPT)) {
                after = new Date(after.getTime() + 60 * 1000);
                EventResume resume = new EventResume(game, after);
                eventService.addEvent(resume);
                game.addEvent(resume);
            }
            EventEnd end = new EventEnd(game, new Date(after.getTime() + 60*1000));
            eventService.addEvent(end);
            game.addEvent(end);
        }

        return "redirect:/games";
    }
}
