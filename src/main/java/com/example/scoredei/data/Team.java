package com.example.scoredei.data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlRootElement
public class Team {
    
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String name;
    @Column(length = 1024)
    private String imageURL;
    @OneToMany(mappedBy="team")
    private List<Player> players;
    @ManyToMany(mappedBy = "teams")
    private List<Game> games;

    // This is a constructor.
    public Team() {
    }

    // This is a constructor.
    public Team(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
        this.players = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    /**
     * This function returns the id of the object
     * 
     * @return The id of the object.
     */
    public int getId() {
        return id;
    }

    /**
     * This function sets the id of the object to the id passed in as a parameter
     * 
     * @param id The id of the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This function returns the name of the person
     * 
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * This function sets the name of the object to the name passed in as a parameter
     * 
     * @param name The name of the parameter.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This function returns the imageURL of the current object
     * 
     * @return The imageURL is being returned.
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * This function sets the imageURL of the current object to the imageURL passed in as a parameter
     * 
     * @param imageURL The URL of the image to be displayed.
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * This function returns a list of players
     * 
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * This function sets the players of the team
     * 
     * @param players The list of players in the game.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * This function returns a list of games
     * 
     * @return A list of games.
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * This function sets the games list to the games list passed in as a parameter
     * 
     * @param games The list of games that the user has played.
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }

    /**
     * The toString() method returns a string representation of the object
     * 
     * @return The id, name, and imageURL of the team.
     */
    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", name=" + name + ", imageURL=" + imageURL + '}';
    }

    /**
     * If the name and imageURL are not empty, return true, otherwise return false.
     * 
     * @return The method isCompleted() returns a boolean value.
     */
    public boolean isCompleted() {
        return !this.name.isEmpty() && !this.imageURL.isEmpty();
    }
}