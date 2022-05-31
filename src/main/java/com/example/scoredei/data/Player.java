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

    // This is a constructor.
    public Player() {
        this.birthDate = null;
        this.position = null;
        this.team = null;
    }

    // This is a constructor. It is used to create an object of the class.
    public Player(String name, Date birthDate, PlayerType position, Team team, String imageURL) {
        this.name = name;
        this.birthDate = birthDate;
        this.position = position;
        this.team = team;
        this.imageURL = imageURL;
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
     * This function returns the birth date of the person
     * 
     * @return The birthDate variable is being returned.
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * This function sets the birthDate of the person to the birthDate passed in as a parameter
     * 
     * @param birthDate The date of birth of the person.
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * This function returns the position of the player
     * 
     * @return The position of the player.
     */
    public PlayerType getPosition() {
        return position;
    }

    /**
     * This function sets the position of the player
     * 
     * @param position The position of the player.
     */
    public void setPosition(PlayerType position) {
        this.position = position;
    }

    /**
     * This function returns the team of the player
     * 
     * @return The team object.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * This function sets the team of the player
     * 
     * @param team The team that the player is on.
     */
    public void setTeam(Team team) {
        this.team = team;
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
     * This function returns the imageURL of the current object
     * 
     * @return The imageURL is being returned.
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * The toString() method returns a string representation of the object
     * 
     * @return The toString method is being returned.
     */
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

    /**
     * "If the name is not empty, the imageURL is not empty, the birthDate is not null, the position is
     * not null, and the team is not null, then return true."
     * 
     * The function isCompleted() is a boolean function, which means it returns either true or false
     * 
     * @return A boolean value.
     */
    public boolean isCompleted() {
        return !this.name.isEmpty()
            && !this.imageURL.isEmpty()
            && this.birthDate != null
            && this.position != null
            && this.team != null;
    }


}
