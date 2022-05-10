package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class EventInterrupt extends Event {

    public EventInterrupt() {
        super();
    }
    
    public EventInterrupt(Date time) {
        super(time);
    }

}
