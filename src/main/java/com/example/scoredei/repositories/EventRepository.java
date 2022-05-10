package com.example.scoredei.repositories;

import com.example.scoredei.data.Event;

import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    
}
