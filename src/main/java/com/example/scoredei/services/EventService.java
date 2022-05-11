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

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        return events;
    }
}
