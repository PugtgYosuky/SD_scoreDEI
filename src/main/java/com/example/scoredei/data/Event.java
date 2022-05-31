package com.example.scoredei.data;

import com.example.scoredei.data.types.EventType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Event implements Comparable<Event> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date time;
    @ManyToOne
    private Game game;

    private EventType type;

    // This is a constructor that takes in no parameters and sets the type to null.
    public Event() {

    }

    // This is a constructor that takes in an EventType and sets the type to the parameter passed in.
    public Event(EventType type) {
        this.type = type;
    }
    
    // A constructor that takes in an EventType and Game and sets the type and game to the parameters
    // passed in.
    public Event(EventType type, Game game){
        this.type = type;
        this.game = game;
    }
    
    // A constructor that takes in an EventType, Game, and Date and sets the type, game, and time to
    // the parameters passed in.
    public Event(EventType type, Game game, Date time){
        this.type = type;
        this.game = game;
        this.time = time;
    }

    /**
     * This function returns the id of the current object
     * 
     * @return The id of the object.
     */
    public int getId(){
        return this.id;
    }

    /**
     * This function sets the id of the object to the id passed in as a parameter
     * 
     * @param id The id of the user.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * This function returns the time of the event
     * 
     * @return The time of the event.
     */
    public Date getTime(){
        return this.time;
    }

    /**
     * This function sets the time of the event.
     * 
     * @param time The time of the event
     */
    public void setTime(Date time){
        this.time = time;
    }

    /**
     * This function returns the game object
     * 
     * @return The game object.
     */
    public Game getGame(){
        return this.game;
    }

    /**
     * This function sets the game variable to the game variable passed in
     * 
     * @param game The game object that the player is in.
     */
    public void setGame(Game game){
        this.game = game;
    }

    /**
     * This function returns the type of the event
     * 
     * @return The type of the event.
     */
    public EventType getType(){
        return this.type;
    }

    /**
     * This function sets the type of the event
     * 
     * @param type The type of event.
     */
    public void setType(EventType type){
        this.type = type;
    }

    /**
     * The toString() method returns a string representation of the object
     * 
     * @return The toString() method is being returned.
     */
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", time=" + time +
                ", game=" + game +
                ", type=" + type +
                '}';
    }

    /**
     * It compares the time of the event to the time of the other event.
     * 
     * @param o The object to be compared.
     * @return The time of the event.
     */
    @Override
    public int compareTo(Event o) {
        return time.compareTo(o.getTime());
    }
}
