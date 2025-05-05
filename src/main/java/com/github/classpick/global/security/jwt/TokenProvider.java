package com.github.classpick.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.classpick.global.property.JwtProperty;
import com.github.classpick.global.security.exception.JwtAuthenticationException;
import com.github.classpick.global.security.exception.JwtAuthenticationExceptionCode;
import com.github.classpick.global.security.oauth.CustomUserDetailsService;
import com.github.classpick.user.repository.UserEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperty jwtProperty;
    private final CustomUserDetailsService customUserDetailsService;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(jwtProperty.getKey());
    }

    public String generateToken(UserEntity user) {
        return JWT.create()
                .withIssuedAt(Instant.now())
                .withClaim("id", user.getUserId())
                .withClaim("role", user.getRole().name())
                .sign(algorithm);
    }

    public DecodedToken decodeToken(String token) {
        var decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);

        String id = decodedJWT.getClaim("id").asString();
        String role = decodedJWT.getClaim("role").asString();

        return new DecodedToken(id, role);
    }

    public boolean validateToken(String token) {
        try {
            if (token == null) {
                throw new JwtAuthenticationException(
                        JwtAuthenticationExceptionCode.TOKEN_MISSING.getMessage(),
                        JwtAuthenticationExceptionCode.TOKEN_MISSING.getCode());
            }
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new JwtAuthenticationException(
                    JwtAuthenticationExceptionCode.INVALID_TOKEN.getMessage(),
                    JwtAuthenticationExceptionCode.INVALID_TOKEN.getCode());
        }
    }

    public Authentication getAuthentication(String accessToken) {
        DecodedToken decoded = decodeToken(accessToken);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(decoded.id());

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                accessToken,
                Collections.singleton(new SimpleGrantedAuthority(decoded.role()))
        );
    }

    public record DecodedToken(String id, String role) {}

}
