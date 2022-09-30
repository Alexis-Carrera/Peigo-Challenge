package com.peigo.challenge.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.peigo.challenge.util.Constant.USER_ID;

@Log4j2
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
            "/login", "POST");

    public AuthenticationFilter(AuthenticationManager manager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, manager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        AuthenticationRequest applicationUser = null;
        try {
            applicationUser = new ObjectMapper().readValue(req.getInputStream(),
                    AuthenticationRequest.class);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    applicationUser.getCustomerId(), applicationUser.getPassword(), Collections.emptyList());
            return getAuthenticationManager().authenticate(auth);
        } catch (AuthenticationException e) {
            throw new PasswordAuthenticationException("Invalid Password", e);
        } catch (Exception e) {
            throw new PasswordAuthenticationException("Unexpected exception: " + e.getMessage(),
                    e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        String token = Jwts.builder()
                .claim(USER_ID, auth.getPrincipal().toString())
                .setSubject("Peigo")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 15)))
                .compact();
        res.addHeader("token", token);
    }
}