package com.team32.ong.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String KEY;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds;

    public String generateToken(UserDetails userDetails){

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        return userDetails
                .getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token).getBody();
    }
}
