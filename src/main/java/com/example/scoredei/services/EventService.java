package com.example.scoredei.services;

import java.util.*;

import com.example.scoredei.data.Event;
import com.example.scoredei.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    /**
     * This function takes an event object and saves it to the database
     * 
     * @param event The event object that is being passed in from the controller.
     */
    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    /**
     * Get all events from the database and add them to a list of events.
     * 
     * @return A list of events
     */
    public List<Event> getEvents() {
    /**
     * This function deletes an event from the database
     * 
     * @param event The event to be deleted
     * @return A boolean value.
     */
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        return events;
    }

    /**
     * This function deletes an event from the database
     * 
     * @param event The event to be deleted
     * @return A boolean value.
     */
    public boolean deleteEvent(Event event){
        try {
            this.eventRepository.delete(event);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
