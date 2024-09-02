package com.example.speedoTransfer.config;

import com.example.speedoTransfer.service.JwtUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
public class AuthenticateConfig {

    private final JwtUserDetailsService jwtUserDetailsService;


    public AuthenticateConfig(JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jwtUserDetailsService.userDetailsService());
        authenticationProvider.setPasswordEncoder(jwtUserDetailsService.passwordEncoder());
        return authenticationProvider;
    }


}
