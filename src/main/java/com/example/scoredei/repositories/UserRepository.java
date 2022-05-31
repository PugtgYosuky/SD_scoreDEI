package com.example.scoredei.repositories;

import com.example.scoredei.data.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * "Find a user by their username, and return the user as an Optional."
     * 
     * The @Query annotation is used to specify a custom JPQL query. The ?1 is a positional parameter
     * 
     * @param username The username of the user you want to find.
     * @return Optional<User>
     */
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);
    
}
