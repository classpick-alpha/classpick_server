package com.github.classpick.reservation.service;


import com.github.classpick.user.repository.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserGetterImplTmp implements UserGetter {
    @Override
    public Long getUserId() {
        return 1L;
    }

    @Override
    public UserEntity getUserEntity() {
        return null;
    }
}
