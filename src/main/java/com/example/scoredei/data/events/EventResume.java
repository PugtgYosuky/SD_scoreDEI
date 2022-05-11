package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;
import com.example.scoredei.data.Game;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class EventResume extends Event {

    public EventResume() {
        super();
    }
    
    public EventResume(Game game) {
        super(game);
    }

    public EventResume(Game game, Date time) {
        super(game, time);
    }

}
