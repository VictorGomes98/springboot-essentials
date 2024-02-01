package com.devdojo.springboot.security.conditions;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AnimesAllCondition {
    public boolean isAuthorized() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .map(Object::toString).anyMatch(a -> a.equals("read"));
    }
}
