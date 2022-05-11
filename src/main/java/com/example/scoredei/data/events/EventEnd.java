package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class EventEnd extends Event {

    public EventEnd() {
        super();
    }

    public EventEnd(Game game){
        super(game);
    }
    
    public EventEnd(Game game, Date time) {
        super(game, time);
    }

}
