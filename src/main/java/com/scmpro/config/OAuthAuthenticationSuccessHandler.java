package com.scmpro.config;

import org.springframework.stereotype.Component;

import com.scmpro.entities.Providers;
import com.scmpro.entities.User;
import com.scmpro.helper.AppConstant;
import com.scmpro.repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    private User orElse;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OAuth");
        // Fetching Data And Storing in Database
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
    
        String email = user.getAttribute("email").toString();
        String name = user.getAttribute("name").toString();
        String picture = user.getAttribute("picture").toString();

        // Create user and Save Into adatabase
        User user1 = new User();
        user1.setEmail(email);
        user1.setName(name);
        user1.setProfilePic(picture);
        user1.setPassword("password");
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderUserId(user.getName());
        user1.setRoleList(List.of(AppConstant.ROLE_USER));
        user1.setAbout("This User Login With Google..");

        User user2 = userRepo.findByEmail(email).orElse(null);
        // If User Not Exist In Database Then Save Into Database
        if (user2 == null)
            userRepo.save(user1);
        
       
        // Redirecting to User Profile Page
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");


    }

}
