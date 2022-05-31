package com.example.scoredei.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * It's a controller that handles the error page
 */
@Controller
public class MyErrorController implements ErrorController {

    /**
     * If the user tries to access a page that doesn't exist, they will be redirected to the 404 page
     * 
     * @param model The model object is used to pass data to the view.
     * @return A string
     */
    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("message", "We couldn't find this page, sorry :(");
        model.addAttribute("prev", "/games");
        return "404";
    }

}
