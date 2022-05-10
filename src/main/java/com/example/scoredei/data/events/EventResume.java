package com.example.scoredei.data.events;

import com.example.scoredei.data.Event;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class EventResume extends Event {

    public EventResume() {
        super();
    }
    
    public EventResume(Date time) {
        super(time);
    }

}
