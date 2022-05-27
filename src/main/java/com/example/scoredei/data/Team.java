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
    @ManyToMany
    private List<Game> games;

    public Team() {
    }

    public Team(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
        this.players = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", name=" + name + ", imageURL=" + imageURL + '}';
    }
}