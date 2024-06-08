package org.example.networktechnologieslab.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.networktechnologieslab.commonTypes.UserRole;
import org.example.networktechnologieslab.infrastructure.entity.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // authenticates users based on the value given in the tokens
    private long tokenLifetime =1000*60*24;

    @Value("${jwt.token}")
    private String jwtSigningKey;

    public String generateToken(Auth userDetail){
        return generateToken(new HashMap<>(), userDetail);
    }

    public UserRole extractRole(String token){
        String roleString = extractClaim(token, (claims) -> claims.get("role", String.class));
        return UserRole.valueOf(roleString);
    }

    public boolean isTokenValid(String token){
        try{
            return !isTokenExpired(token);
        } catch (Exception e){
            return false;
        }
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }
    private boolean verify(String token){
        return true;
    }

    private String generateToken(Map<String, Object> extraClaims, Auth userDetails){
        extraClaims.put("role", userDetails.getRole());
        extraClaims.put("userId", userDetails.getUserId());
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLifetime))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
