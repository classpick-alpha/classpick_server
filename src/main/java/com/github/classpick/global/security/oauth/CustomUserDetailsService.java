package com.github.classpick.global.security.oauth;

import com.github.classpick.global.security.exception.JwtAuthenticationException;
import com.github.classpick.global.security.exception.JwtAuthenticationExceptionCode;
import com.github.classpick.user.repository.UserEntity;
import com.github.classpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findById(Long.valueOf(username))
                .orElseThrow(() -> new JwtAuthenticationException(JwtAuthenticationExceptionCode.INVALID_TOKEN));

        return new OAuth2GoogleUser(userEntity);
    }
}
