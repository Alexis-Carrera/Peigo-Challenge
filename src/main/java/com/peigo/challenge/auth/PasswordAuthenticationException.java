package com.peigo.challenge.auth;

import org.springframework.security.core.AuthenticationException;

public class PasswordAuthenticationException extends AuthenticationException {

    public PasswordAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
