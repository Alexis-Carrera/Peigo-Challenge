package com.peigo.challenge.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;

public class PasswordAuthenticationManager implements AuthenticationManager {


    public PasswordAuthenticationManager() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            if(!authentication.getCredentials().toString().equals("Alexis")){
                throw new RuntimeException();
            }
            UsernamePasswordAuthenticationToken authReturn = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(), null,
                    Collections.emptyList());
            return authReturn;
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
