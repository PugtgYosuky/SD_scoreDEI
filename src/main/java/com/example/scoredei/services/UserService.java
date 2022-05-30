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

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

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

    public User authenticate(String username, String password) {
        Optional<User> u = userRepository.findByUsername(username);
        if(u.isEmpty() || !u.get().samePassword(password)){
            return null;
        }
        return u.get();
    }
    
}
