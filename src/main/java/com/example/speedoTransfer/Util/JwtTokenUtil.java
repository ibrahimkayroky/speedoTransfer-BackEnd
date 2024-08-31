package com.example.speedoTransfer.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    public static final long TokenValidity = 5 * 60 * 60;
    private final String SigningKey = "secret";
    public String getUsernameFromToken(String token)
    {
        return getClaimFromToken(token , Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(SigningKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims = new HashMap<>();
        return GenerateToken(claims,userDetails.getUsername());
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String GenerateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims) //add ccustom claims
                .setSubject(username) //for whom the token is issued
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TokenValidity * 1000)) //because it converted to milliseconds
                .signWith(SignatureAlgorithm.HS512,SigningKey) //hash with 512bit key
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails)
    {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}

