package com.scmpro.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scmpro.entities.User;
import com.scmpro.helper.EmailHelper;
import com.scmpro.servcies.UserService;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private User user;


    // User dashboard page
    @GetMapping("/dashboard")
    public String getUserDashboard() {
        return "user/dashboard";
    }

    // User profile page
    @GetMapping("/profile")
    public String getUserProfile(Authentication authentication, Model model) {

        var user = userService.getUserByEmail(EmailHelper.getEmailOfLogedUser(authentication));
        model.addAttribute("user", user);
        

        return "user/profile";
    }
}
