package com.ip737.transportcompany.transportcompany.configs.security.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFacade {

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UserDetails jwtUser = (UserDetails) authentication.getPrincipal();
            return jwtUser.getUsername();
        }

        return null;
    }

    public String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UserDetailsImpl jwtUser = (UserDetailsImpl) authentication.getPrincipal();
            return jwtUser.getRole();
        }

        return null;
    }

    public String getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UserDetailsImpl jwtUser = (UserDetailsImpl) authentication.getPrincipal();
            return jwtUser.getId();
        }

        return null;
    }

    public boolean isAllowed(String expected) {
        return getRole().equals(expected);
    }
}
