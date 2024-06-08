package org.example.networktechnologieslab.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@RestController
public class LoginController {

    @Value("${jwt.token}")
    private String key;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        // get user from db by login
        if (true) {
            // check password
            long millis = System.currentTimeMillis();
            String token = Jwts.builder()
                    .issuedAt(Date.from(Instant.now()))
                    .expiration(new Date (millis + 5 * 60 * 100)) // 5 minutes
                    .claim("id", "userId") //insert user id
                    .claim("role", "USER") //insert user role
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();

            return new ResponseEntity<>(token, HttpStatus.OK);
        } else { //if password is incorrect or user does not exist
            return new ResponseEntity<>("Wrong login or password", HttpStatus.UNAUTHORIZED);
        }
    }
}