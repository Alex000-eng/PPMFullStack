package edu.cornell.PPMFullStack.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import edu.cornell.PPMFullStack.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
    // Generate the token
    public String generateToken(Authentication authentication) {
        User user= (User) authentication.getPrincipal();

        Date now= new Date(System.currentTimeMillis());

        Date expireDate= new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

        String userId= Long.toString(user.getId());

        Map<String, Object> claims= new HashMap<>();

        claims.put("id", Long.toString(user.getId()));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
            .setSubject(userId)
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
            .compact();
    }
    // Validate the token

    // Get user id
}
