package com.example.scoredei.services;

import java.util.*;

import com.example.scoredei.data.User;
import com.example.scoredei.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * It takes a user object, saves it to the database, and returns the saved user object
     * 
     * @param user The user object that is to be added to the database.
     */
    public void addUser(User user) {
        userRepository.save(user);
    }

    /**
     * For each user in the database, add it to the list of users.
     * 
     * @return A list of users
     */
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Find the user by username
     * 
     * @param username The username of the user to retrieve.
     * @return Optional<User>
     */
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find the user by id
     * 
     * @param id The id of the user you want to get.
     * @return An Optional object.
     */
    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

    /**
     * This function deletes a user from the database
     * 
     * @param id the id of the user to be deleted
     * @return A boolean value.
     */
    public boolean deleteUser(int id){
        Optional<User> u = userRepository.findById(id);
        if(u.isEmpty()) {
            return false;
        }
        try {
            userRepository.deleteById(id);
        } catch ( IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * If the user exists and the password is correct, return the user. Otherwise, return null
     * 
     * @param username The username of the user
     * @param password the password that the user entered
     * @return The user object is being returned.
     */
    public User authenticate(String username, String password) {
        Optional<User> u = userRepository.findByUsername(username);
        if(u.isEmpty() || !u.get().samePassword(password)){
            return null;
        }
        return u.get();
    }
    
}
