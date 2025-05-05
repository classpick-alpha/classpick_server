package com.github.classpick.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.classpick.global.dto.Response;
import com.github.classpick.global.security.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String accessToken = extractToken(request);

        try {
            if (accessToken != null && tokenProvider.validateToken(accessToken)) {
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtAuthenticationException e) {
            handleException(response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");

        return (authorization != null && authorization.startsWith("Bearer ")) ? authorization.substring(7) : null;
    }

    private void handleException(HttpServletResponse response, JwtAuthenticationException e) throws IOException {

        Response<?> apiResponse = Response.error(e);

        String content = new ObjectMapper().writeValueAsString(apiResponse);

        response.addHeader("Content-Type", "application/json");
        response.getWriter().write(content);
        response.getWriter().flush();
    }
}
