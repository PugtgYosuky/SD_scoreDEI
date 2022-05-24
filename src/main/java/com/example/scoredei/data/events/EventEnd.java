package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;
import com.example.scoredei.data.types.EventType;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class EventEnd extends Event {

    public EventEnd() {
        super(EventType.END);
    }

    public EventEnd(Game game){
        super(EventType.END, game);
    }
    
    public EventEnd(Game game, Date time) {
        super(EventType.END, game, time);
    }

}
