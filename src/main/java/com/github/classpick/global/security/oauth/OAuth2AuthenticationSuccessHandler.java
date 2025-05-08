package com.github.classpick.global.security.oauth;

import com.github.classpick.global.property.OauthProperty;
import com.github.classpick.global.security.jwt.TokenProvider;
import com.github.classpick.user.repository.Role;
import com.github.classpick.user.repository.UserEntity;
import com.github.classpick.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String KOOKMIN_EMAIL_REGEX = ".*@kookmin\\.ac\\.kr$";
    private final OauthProperty oauthProperty;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2GoogleUser oauth2User = (OAuth2GoogleUser) authentication.getPrincipal();
        String email = oauth2User.getEmail();

        if (!email.matches(KOOKMIN_EMAIL_REGEX)) {
            getRedirectStrategy().sendRedirect(
                    request,
                    response,
                    String.format("%s?error=NOT_KOOKMIN_EMAIL", oauthProperty.getFailure())
            );

            return;
        }

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(UserEntity.builder()
                        .role(Role.USER)
                        .email(oauth2User.getEmail())
                        .build()));

        String accessToken = tokenProvider.generateToken(user);

        getRedirectStrategy().sendRedirect(
                request,
                response,
                String.format("%s?token=%s", oauthProperty.getSuccess(), accessToken)
        );
    }
}
