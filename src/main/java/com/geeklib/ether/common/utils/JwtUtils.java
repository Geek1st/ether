package com.geeklib.ether.common.utils;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {
    private static final String JWT_SECRET = "MYe0m9U7WGmkHESUBENv33mOq8bVtFsZ";
    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours
    private static final Key JWT_KEY = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

    public static String generateToken(String username){
        return Jwts.builder().setSubject(username).setExpiration(new java.util.Date(System.currentTimeMillis() + JWT_EXPIRATION)).signWith(JWT_KEY).compact();
    }

    public static Claims parseToken(String token){
        return Jwts.parserBuilder().setSigningKey(JWT_KEY).build().parseClaimsJws(token).getBody();
    }

    public static String getUsername(String token){
        return parseToken(token).getSubject();
    }
}
