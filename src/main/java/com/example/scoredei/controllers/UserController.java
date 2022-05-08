package com.example.scoredei.controllers;

import com.example.scoredei.data.User;
import com.example.scoredei.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String getUsers(Model model) {
        model.addAttribute("users", this.userService.getUsers());
        return "users";
    }
    
    @GetMapping("/add-user") 
    public String addUser() {
        User user = new User("asakjs", "asakjs", "asakjs", "asdasd", "jhasdhk");
        userService.addUser(user);
        return "redirect:/";
    }
    

}
