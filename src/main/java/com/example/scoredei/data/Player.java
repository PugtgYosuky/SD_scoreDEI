package com.example.scoredei.data;

import com.example.scoredei.data.types.PlayerType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement
public class Player {
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private PlayerType position;
    @ManyToOne
    private Team team;
    private String imageURL;

    public Player() {
        this.birthDate = null;
        this.position = null;
        this.team = null;
    }

    public Player(String name, Date birthDate, PlayerType position, Team team, String imageURL) {
        this.name = name;
        this.birthDate = birthDate;
        this.position = position;
        this.team = team;
        this.imageURL = imageURL;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public PlayerType getPosition() {
        return position;
    }

    public void setPosition(PlayerType position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", position=" + position +
                ", team=" + team +
                '}';
    }

    public boolean isCompleted() {
        return !this.name.isEmpty()
            && !this.imageURL.isEmpty()
            && this.birthDate != null
            && this.position != null
            && this.team != null;
    }


}
