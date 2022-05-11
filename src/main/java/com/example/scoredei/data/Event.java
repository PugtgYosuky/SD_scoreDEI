package com.example.scoredei.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date time;
    @ManyToOne
    private Game game;

    public Event() {

    }
    public Event(Game game){
        this.game = game;
    }
    
    public Event(Game game, Date time){
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
}
