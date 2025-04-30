package com.github.classpick.security.oauth;

import com.github.classpick.security.jwt.TokenProvider;
import com.github.classpick.user.repository.Role;
import com.github.classpick.user.repository.UserEntity;
import com.github.classpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> registerNewUser(email, name));

        return new OAuth2GoogleUser(user);
    }

    private UserEntity registerNewUser(String email, String name) {
        Role role = determineRoleByDB(email);

        UserEntity newUser = UserEntity.builder()
                .email(email)
                .name(name)
                .role(role)
                .build();

        return userRepository.save(newUser);
    }

    private Role determineRoleByDB(String email) {
        if (userRepository.existsByEmailAndRole(email, Role.MANAGER)) {
            return Role.MANAGER;
        } else {
            return Role.USER;
        }
    }
}
