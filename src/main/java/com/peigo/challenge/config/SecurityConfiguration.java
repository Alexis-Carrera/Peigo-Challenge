package com.peigo.challenge.config;

import com.peigo.challenge.auth.AuthenticationFilter;
import com.peigo.challenge.auth.PasswordAuthenticationException;
import com.peigo.challenge.auth.PasswordAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        PasswordAuthenticationManager manager = new PasswordAuthenticationManager();
        AuthenticationFilter authFilter = new AuthenticationFilter(manager);
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll()
                .and()
                .headers().cacheControl().disable()
                .and()
                .addFilterBefore(authFilter, BasicAuthenticationFilter.class);
        return http.build();
    }
}
