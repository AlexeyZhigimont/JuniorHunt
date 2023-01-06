package com.juniorhunt.controller;

import com.juniorhunt.model.User;
import com.juniorhunt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(User user, Model model) {
        final String message;
        if (userService.save(user)) {
            message = "Registration successful!";
            return "redirect:/login";
        }
        message = "This user exists!";
        model.addAttribute("message", message);
        return "registration";
    }
}
