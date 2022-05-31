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

/**
 * Controller that handles all the admin requests.
 */
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

    /**
     * This function creates a new FilterRegistrationBean, sets the filter to be the AdminFilter, adds
     * the url pattern to be /admin/*, and sets the order to be 1.
     * 
     * @return A FilterRegistrationBean object.
     */
    @Bean
    public FilterRegistrationBean<AdminFilter> adminLoggingFilter(){
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter(userService));
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    /**
     * This function is called when the user navigates to the /add-user URL. It creates a new User
     * object and adds it to the model. It then returns the add-user view
     * 
     * @param model This is the model object that will be used to pass data to the view.
     * @return The add-user.html file
     */
    @GetMapping("/add-user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    /**
     * If the user is not completed, return an invalid page. If the user is completed, add the user to
     * the database
     * 
     * @param user The user object that is being created
     * @param model The model object that is used to pass data to the view.
     * @return A String
     */
    @PostMapping("/create-user")
    public String createUser(@ModelAttribute User user, Model model) {
        if(!user.isCompleted()) {
            model.addAttribute("message", "Invalid fields");
            model.addAttribute("prev", "/admin/add-user");
            return "invalid";
        }
        if(userService.getUser(user.getUsername()).isPresent()) {
            model.addAttribute("message", "There is already a user with that username");
            model.addAttribute("prev", "/admin/add-user");
            return "invalid";
        }
        user.encrypt();
        this.userService.addUser(user);
        return "redirect:/admin/users";
    }

    /**
     * This function is called when the user navigates to the /add-team URL. It creates a new Team
     * object and adds it to the model. It then returns the add-team view
     * 
     * @param model This is the model that will be passed to the view.
     * @return A new team object
     */
    @GetMapping("/add-team")
    public String addTeam(Model model) {
        model.addAttribute("team", new Team());
        return "add-team";
    }

    /**
     * If the team is not completed, return an error message. If the team is completed, add the team to
     * the database
     * 
     * @param team the team object
     * @param model The model object that is used to pass data to the view.
     * @return A string
     */
    @PostMapping("/create-team")
    public String createTeam(@ModelAttribute Team team, Model model) {
        if(!team.isCompleted()) {
            model.addAttribute("message", "Invalid fields");
            model.addAttribute("prev", "/admin/add-team");
            return "invalid";
        }
        if(teamService.getTeam(team.getName()).isPresent()) {
            model.addAttribute("message", "There is already a team with that name");
            model.addAttribute("prev", "/admin/add-team");
            return "invalid";
        }
        this.teamService.addTeam(team);
        return "redirect:/teams";
    }

   /**
    * This function is used to add a game to the database.
    * 
    * @param model The model is a Map of object names to object values. The model is used to pass data
    * from the controller to the view.
    * @return A String with the html file name
    */
    @GetMapping("/add-game")
    public String addGame(Model model) {
        model.addAttribute("gameForm", new GameForm());
        model.addAttribute("teams", this.teamService.getTeams());
        return "add-game";
    }

    /**
     * It creates a game between two teams, and if the game is not completed, it returns an error
     * message
     * 
     * @param gameForm a class that contains the game and the two teams
     * @param model The model is a Map of key/value pairs which the view can use to render dynamic
     * content.
     * @return A String
     */
    @PostMapping("/create-game")
    public String createGame(@ModelAttribute GameForm gameForm, Model model) {
        gameForm.getGame().addTeam(gameForm.getTeamA());
        gameForm.getGame().addTeam(gameForm.getTeamB());
        if(!gameForm.getGame().isCompleted()) {
            model.addAttribute("message", "Invalid fields");
            model.addAttribute("prev", "/admin/add-game");
            return "invalid";
        }
        if(gameForm.getTeamA().getName().equals(gameForm.getTeamB().getName())) {
            model.addAttribute("message", "You can't create a game between the same team");
            model.addAttribute("prev", "/admin/add-game");
            return "invalid";
        }
        this.gameService.addGame(gameForm.getGame());
        return "redirect:/games";
    }

    /**
     * This function is used to add a player to the database
     * 
     * @param model This is the model that will be passed to the view.
     * @return A string that is the name of the html file.
     */
    @GetMapping("/add-player")
    public String addPlayer(Model model) {
        model.addAttribute("player", new Player());
        model.addAttribute("teams", this.teamService.getTeams());
        return "add-player";
    }

    /**
     * It takes a player object, checks if it's valid, and if it is, it adds it to the database
     * 
     * @param player the player object that is being created
     * @param model The model is a Map of model objects, which can be used to pass data to the view.
     * @return A string that is the name of the html file.
     */
    @PostMapping("/create-player")
    public String createPlayer(@ModelAttribute Player player, Model model) {
        if(!player.isCompleted()) {
            model.addAttribute("message", "Invalid fields");
            model.addAttribute("prev", "/admin/add-user");
            return "invalid";
        }
        this.playerService.addPlayer(player);
        return "redirect:/players";
    }

    // *********************** GET USERS ***********************

    /**
     * The function getUsers() is a function that returns a list of users
     * 
     * @param model This is the model object that is used to pass data from the controller to the view.
     * @return A string that is the name of the html file.
     */
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", this.userService.getUsers());
        return "users";
    }

    // *********************** EDIT METHODS ***********************

    /**
     * If the team exists, then return the edit-team page with the team's information. Otherwise,
     * redirect to the teams page
     * 
     * @param model The model object is used to pass data from the controller to the view.
     * @param id the id of the team to edit
     * @return A string that is the name of the html file.
     */
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

    /**
     * This function takes a Team object as a parameter, and then saves it to the database.
     * 
     * @param team the team object that is being edited
     * @return A redirect to the teams page.
     */
    @PostMapping("/save-edit-team")
    public String saveEditTeam(@ModelAttribute Team team) {
        this.teamService.addTeam(team);
        return "redirect:/teams";
    }

    /**
     * If the player exists, then add the player to the model and return the edit-player page.
     * Otherwise, redirect to the players page
     * 
     * @param model The model is a map of values that will be passed to the view.
     * @param id the id of the player to edit
     * @return The edit-player.html page
     */
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

    /**
     * The function takes a player object as a parameter, and then saves the player object to the
     * database
     * 
     * @param player the player object that is being edited
     * @return A redirect to the players page.
     */
    @PostMapping("/save-edit-player")
    public String saveEditPlayer(@ModelAttribute Player player) {
        this.playerService.addPlayer(player);
        return "redirect:/players";
    }

    /**
     * If the game is present, add the game to the model and return the edit-game page
     * 
     * @param id the id of the game to edit
     * @param model The model is a Map of model objects, which can be used to pass data to the view.
     * @return A string that is the name of the html file.
     */
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

    /**
     * The function takes a GameForm object as a parameter, sets the teamA and teamB properties of the
     * Game object in the GameForm object to the teamA and teamB properties of the GameForm object, and
     * then saves the Game object in the GameForm object to the database.
     * 
     * @param gameForm 
     * @return A redirect to the games page.
     */
    @PostMapping("/save-edit-game")
    public String saveEditGame(@ModelAttribute GameForm gameForm) {
        gameForm.getGame().setTeamA(gameForm.getTeamA());
        gameForm.getGame().setTeamB(gameForm.getTeamB());
        this.gameService.addGame(gameForm.getGame());
        return "redirect:/games";
    }

    /**
     * This function takes in a user id, and if the user exists, it returns the edit-user page with the
     * user's information. If the user doesn't exist, it returns the 404 page with a message
     * 
     * @param id the id of the user to edit
     * @param model The model is an object that holds data that you want to pass to the view.
     * @return A string that is the name of the html file.
     */
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

    /**
     * It takes a user object from the form, saves it to the database, and then redirects to the
     * admin/users page
     * 
     * @param user the user object that is being edited
     * @return A redirect to the admin/users page.
     */
    @PostMapping("/save-edit-user")
    public String saveEditUser(@ModelAttribute User user) {
        this.userService.addUser(user);
        return "redirect:/admin/users";
    }

    // *********************** DELETE METHODS ***********************

    /**
     * If the game is deleted, redirect to the games page, otherwise, return a 404 page
     * 
     * @param id the id of the game to be deleted
     * @param model The model is an object that holds data that you want to pass to the view.
     * @return A string that is the name of the html file.
     */
    @PostMapping("/delete-game")
    public String deleteGame(@RequestParam(name="id", required=true) int id, Model model) {
        if(this.gameService.deleteGame(id))
            return "redirect:/games";
        model.addAttribute("prev", "/games");
        model.addAttribute("message", "Game not found");
        return "404";
    }

    /**
     * If the player is deleted, redirect to the players page, otherwise return a 404 page
     * 
     * @param id the id of the player to be deleted
     * @param model The model is an object that holds data that you want to pass to the view.
     * @return A string that is the name of the html file.
     */
    @PostMapping("/delete-player")
    public String deletePlayer(@RequestParam(name="id", required=true) int id, Model model) {
        if(this.playerService.deletePlayer(id))
            return "redirect:/players";
        model.addAttribute("prev", "/players");
        model.addAttribute("message", "Player not found");
        return "404";
    }

    /**
     * It deletes a team from the database and redirects to the teams page
     * 
     * @param id the id of the team to be deleted
     * @param model The model is an object that holds data that you want to pass to the view.
     * @return A string that is the name of the html file.
     */
    @PostMapping("/delete-team")
    public String deleteTeam(@RequestParam(name="id", required=true) int id, Model model) {
        if(this.teamService.deleteTeam(id))
            return "redirect:/teams";
        model.addAttribute("prev", "/teams");
        model.addAttribute("message", "Team not found");
        return "404";
    }

    /**
     * It deletes a user from the database and redirects to the users page
     * 
     * @param id the id of the user to be deleted
     * @param model The model is an object that holds data that you want to pass to the view.
     * @return A string that is the name of the html file.
     */
    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam(name="id", required=true) int id, Model model) {
        if(this.userService.deleteUser(id))
            return "redirect:/admin/users";
        model.addAttribute("prev", "/admin/users");
        model.addAttribute("message", "User not found");
        return "404";
    }


    //************************ POPULATE DATABASE ************************/
    
    /**
     * It takes a team, a teamID and a key as parameters and then it uses the key to get the players of
     * the team with the given teamID and then it adds them to the team
     * 
     * @param team The team object that the players will be added to
     * @param teamID The ID of the team you want to get the players from.
     * @param key API key
     */
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

    /**
     * It gets a list of teams from an API, creates a Team object for each team, and adds it to the
     * database
     * 
     * @return A JSONObject
     */
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

    /**
     * It creates 10 games with 10 random events each
     * 
     * @return A String
     */
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
