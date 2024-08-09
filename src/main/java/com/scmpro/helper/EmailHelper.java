package com.scmpro.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class EmailHelper {

    public static String getEmailOfLogedUser(Authentication authentication) {

        
        if (authentication instanceof OAuth2AuthenticationToken) {

            var aOAuthenticaticationToken = (OAuth2AuthenticationToken) authentication;
            var clintId = aOAuthenticaticationToken.getAuthorizedClientRegistrationId();
            String userName = "";

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            // User of Google
            if (clintId.equalsIgnoreCase("google")) {
                userName = oauth2User.getAttribute("email").toString();
            }

            return userName;

        } else {
            // User Of Email And Password
            System.out.println("Email And Password Login");
            return authentication.getName();
        }

    }

}
