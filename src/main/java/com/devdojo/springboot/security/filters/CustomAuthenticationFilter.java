package com.devdojo.springboot.security.filters;


import com.devdojo.springboot.security.AuthenticationManager.CustomAuthenticationManager;
import com.devdojo.springboot.security.authentication.CustomAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String key = String.valueOf(request.getHeader("key"));
        CustomAuthentication authentication = new CustomAuthentication(false, key);

        Authentication authenticate = customAuthenticationManager.authenticate(authentication);
        if (authenticate.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            filterChain.doFilter(request, response);
        }
    }
}
