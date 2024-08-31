package com.example.speedoTransfer.SecurityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfiguration {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


}
