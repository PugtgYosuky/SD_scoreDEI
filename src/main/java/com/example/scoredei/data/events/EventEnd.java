package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class EventEnd extends Event {

    public EventEnd() {
        super();
    }
    
    public EventEnd(Date time) {
        super(time);
    }

}
