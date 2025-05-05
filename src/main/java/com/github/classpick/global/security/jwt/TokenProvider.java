package com.github.classpick.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.classpick.global.property.JwtProperty;
import com.github.classpick.global.security.oauth.CustomUserDetailsService;
import com.github.classpick.global.security.oauth.OAuth2GoogleUser;
import com.github.classpick.user.repository.UserEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperty jwtProperty;
    private final CustomUserDetailsService customUserDetailsService;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {

        algorithm = Algorithm.HMAC256(jwtProperty.getKey());
    }

    public String generateToken(UserEntity user) {

        return JWT.create().withIssuedAt(Instant.now()).withClaim("id", user.getUserId()).sign(algorithm);
    }

    public String extractToken(String token) {

        return JWT.require(algorithm).build().verify(token).getClaim("id").asString();
    }

    public boolean validateToken(String token) {

        if (token == null) {

            return false;
        }

        JWT.require(algorithm).build().verify(token);

        return true;
    }

    public Authentication getAuthentication(String accessToken) {

        String id = extractToken(accessToken);
        OAuth2GoogleUser userDetails = (OAuth2GoogleUser) customUserDetailsService.loadUserByUsername(id);

        return new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());
    }
}
