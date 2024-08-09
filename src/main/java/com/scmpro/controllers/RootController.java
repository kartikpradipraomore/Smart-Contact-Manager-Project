package com.scmpro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scmpro.helper.EmailHelper;
import com.scmpro.servcies.UserService;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;
    
    @ModelAttribute
    public void addUserAllMethod(Authentication authentication, Model model) {
        if (authentication == null)
            return;
        String userName = EmailHelper.getEmailOfLogedUser(authentication);
        // Fetching user From Database
        model.addAttribute("user", userService.getUserByEmail(userName));
    }
    

}
