package com.github.classpick.reservation.service;


import com.github.classpick.user.repository.UserEntity;

public interface UserGetter {

    Long getUserId();
    UserEntity getUserEntity();

}
