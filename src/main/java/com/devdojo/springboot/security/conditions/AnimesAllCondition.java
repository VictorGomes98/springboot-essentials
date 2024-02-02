package com.devdojo.springboot.security.conditions;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AnimesAllCondition {
    public boolean hasReadAuthorization() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .map(Object::toString).anyMatch(a -> a.equals("read"));
    }
}
