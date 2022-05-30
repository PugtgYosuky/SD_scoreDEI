package com.example.scoredei.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("message", "We couldn't find this page, sorry :(");
        model.addAttribute("prev", "/games");
        return "404";
    }

}
