package com.example.sample.domain.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "customLogin";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
    
}
