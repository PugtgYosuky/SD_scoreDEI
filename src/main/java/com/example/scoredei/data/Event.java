package com.example.scoredei.data;

import com.example.scoredei.data.types.EventType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date time;
    @ManyToOne
    private Game game;

    private EventType type;

    public Event(EventType type) {
        this.type = type;
    }
    
    public Event(EventType type, Game game){
        this.type = type;
        this.game = game;
    }
    
    public Event(EventType type, Game game, Date time){
        this.type = type;
        this.game = game;
        this.time = time;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Date getTime(){
        return this.time;
    }

    public void setTime(Date time){
        this.time = time;
    }

    public Game getGame(){
        return this.game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public EventType getType(){
        return this.type;
    }

    public void setType(EventType type){
        this.type = type;
    }
}
