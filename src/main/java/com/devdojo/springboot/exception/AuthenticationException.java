package com.devdojo.springboot.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class AuthenticationException extends BadCredentialsException {
    public AuthenticationException(String msg) {
        super(msg);
    }
}
