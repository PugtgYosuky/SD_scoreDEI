package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class EventStart extends Event {

    public EventStart() {
        super();
    }
    
    public EventStart(Date time) {
        super(time);
    }

}
