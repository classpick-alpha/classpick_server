package com.github.classpick.global.security.oauth;

import com.github.classpick.global.property.OauthCallbackProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final OauthCallbackProperty oauthCallbackProperty;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        log.error("OAuth2 로그인 실패: {}", exception.getMessage(), exception);

        String redirectUrl = String.format("%s?error=OAUTH2_LOGIN_FAILED", oauthCallbackProperty.getFailure());

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}