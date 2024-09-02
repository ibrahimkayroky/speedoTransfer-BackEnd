package com.example.speedoTransfer.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenUtil {

    public static final long TokenValidity = 5 * 60 * 60;
    private static final String SECRET_KEY = "DvdhCDtTBdY6TZCK45DquX66VC3Zw6Mh4Z42CUfY8JR14Bmf3e9xphvXmHHznuSg";
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

        return  Jwts.parser()
                .setSigningKey(getSignInKey())//sign in key used to create signature part of jwt
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails)
    {
        return GenerateToken(new HashMap<>(),userDetails.getUsername());
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String GenerateToken(Map<String, Object> claims,
                                 String username) {
        return Jwts.builder()
                .setClaims(claims) //add custom claims
                .setSubject(username) //for whom the token is issued
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TokenValidity * 1000)) //because it converted to milliseconds
                .signWith(SignatureAlgorithm.HS256, getSignInKey()) //hash with 512bit key
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails)
    {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}

