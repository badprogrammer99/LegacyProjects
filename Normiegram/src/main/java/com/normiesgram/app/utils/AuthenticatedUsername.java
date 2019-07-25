package com.normiesgram.app.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUsername {

    @Bean
    @Lazy
    public String getAuthenticatedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getPrincipal() != null ? (String) auth.getPrincipal() : null;
    }
}
