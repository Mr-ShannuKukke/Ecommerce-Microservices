package com.ecommerce.product.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:MySecretKeyForJWTTokenGenerationAndValidation2026}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") //24 hours default
    private Long jwtExpiration;

//    Get the signing key for JWT operations

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

//    Generate JWT token for user

    public String generateToken(Long userId, String email){
        Date now=new Date();
        Date expiryDate=new Date(now.getTime()+jwtExpiration);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();

    }

    // Get user id from token

    public Long getUserIdFromToken(String token){
        try {
            return Long.valueOf(Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject()
            );
        }catch (JwtException | NumberFormatException e){
            return null;
        }
    }

    //Get email from token

    public String getEmailFromToken(String token){
        try {
            return (String) Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("Email");
        }catch (JwtException e){
            return null;
        }
    }

//    Validate JWT token

    public boolean validateToken(String token){
        try {
             Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
             return true;
        }catch (SecurityException | MalformedJwtException e){
            System.err.println("Invalid JWT signature: "+e.getMessage());
        }catch (ExpiredJwtException e) {
            System.err.println("Expired JWT token: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Unsupported JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }
     return false;
    }
}
