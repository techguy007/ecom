package com.website.ecommerce.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.website.ecommerce.model.Customer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String SECRET_KEY = "c333a91dea49e1f549f7bceff5afe52f0dab8420ef9270d680634801c3136bbc";


    //get the user name from the claim
    public String extractUsername(String token) {
        if (token == null || token.isEmpty()) {
            // Handle the case where the token is null or empty
            return null;
        }
        return extractClaim(token, Claims::getSubject);
    }
    
    public boolean isValid(String token, UserDetails user) {
        if (token == null || token.isEmpty()) {
            // Handle the case where the token is null or empty
            return false;
        }
        String username = extractUsername(token);
        return (username != null && username.equals(user.getUsername())) && !isTokenExpired(token);
    }
    
    private boolean isTokenExpired(String token) {
        if (token == null || token.isEmpty()) {
            // Handle the case where the token is null or empty
            return true;
        }
        return extractExpiration(token).before(new Date());
    }
    
    private Date extractExpiration(String token) {
        if (token == null || token.isEmpty()) {
            // Handle the case where the token is null or empty
            return null;
        }
        return extractClaim(token, Claims::getExpiration);
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        if (token == null || token.isEmpty()) {
            // Handle the case where the token is null or empty
            return null;
        }
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        if (token == null || token.isEmpty()) {
            // Handle the case where the token is null or empty
            return null;
        }

        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
        
    @SuppressWarnings("deprecation")
    public String generateToken(Customer user) {
    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

    List<String> authorityNames = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    String token = Jwts
            .builder()
            .setSubject(user.getUsername())
            .claim("authorities", authorityNames) //(ROLE) Set authorities in the token claims
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            .signWith(getSigninKey())
            .compact();
    return token;
    }

    private SecretKey getSigninKey(){
        byte [] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
