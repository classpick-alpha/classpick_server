package com.github.classpick.security.oauth;

import com.github.classpick.property.OauthCallbackProperty;
import com.github.classpick.security.jwt.TokenProvider;
import com.github.classpick.user.repository.Role;
import com.github.classpick.user.repository.UserEntity;
import com.github.classpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final OauthCallbackProperty oauthCallbackProperty;

    private final String KOOKMIN_EMAIL_REGEX = ".*@kookmin\\.ac\\.kr$";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2GoogleUser oAuth2User = (OAuth2GoogleUser) authentication.getPrincipal();
        String email = oAuth2User.getEmail();

        if (!email.matches(KOOKMIN_EMAIL_REGEX)) {
            getRedirectStrategy().sendRedirect(request, response, String.format("%s?error=NOT_KOOKMIN_EMAIL", oauthCallbackProperty.getFailure()));
            return;
        }

        boolean isNewUser = !userRepository.existsByEmail(email);

        UserEntity user = userRepository.findByEmail(email)
            .orElseGet(() -> userRepository.save(
                UserEntity.builder()
                    .email(email)
                    .name(oAuth2User.getName())
                    .schoolNumber(null)
                    .userGroup(null)
                    .role(Role.USER)
                    .build()
            ));

        if (Objects.isNull(user)) {
            getRedirectStrategy().sendRedirect(request, response, String.format("%s?error=USER_CREATION_FAILED", oauthCallbackProperty.getFailure()));
            return;
        }

        String accessToken = tokenProvider.generateToken(user);
        getRedirectStrategy().sendRedirect(request, response, String.format("%s?token=%s&isNewUser=%s", oauthCallbackProperty.getSuccess(), accessToken, isNewUser));
    }
}
