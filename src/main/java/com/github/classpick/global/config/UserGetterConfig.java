package com.github.classpick.global.config;

import com.github.classpick.global.security.oauth.OAuth2GoogleUser;
import com.github.classpick.global.user.UserGetter;
import com.github.classpick.user.repository.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class UserGetterConfig {

    @Bean
    public UserGetter userGetter() {

        return new UserGetter() {

            @Override
            public UserEntity getUser() {

                OAuth2GoogleUser principal = (OAuth2GoogleUser) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

                return principal.getUser();
            }
        };
    }
}
