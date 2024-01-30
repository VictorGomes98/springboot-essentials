package com.devdojo.springboot.security.providers;

import com.devdojo.springboot.exception.AuthenticationException;
import com.devdojo.springboot.security.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${Key}")
    private String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthentication customAuthentication = (CustomAuthentication) authentication;

        String headerKey = customAuthentication.key();

        if (key.equals(headerKey)){
            return new CustomAuthentication(true, null);
        }

        throw new AuthenticationException("Incorrect credential");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
