package com.example.speedoTransfer.config;

import com.example.speedoTransfer.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    //To Validate Jwt Token

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")){
            //this token is a JSON web token Authorization: Bearer <jwt_token>
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken = requestTokenHeader.substring(7);
        userEmail = jwtTokenUtil.getUsernameFromToken(jwtToken);//extract from jwt token
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);


            if(jwtTokenUtil.validateJwtToken(jwtToken))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null, //we dont have credentials
                                userDetails.getAuthorities()
                        );

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken
                        );
            }
        }
        filterChain.doFilter(request,response);

    }


}
