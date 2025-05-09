package com.github.classpick.user.controller.dto.response;

import com.github.classpick.user.repository.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class SafeUserResponse {

    long userId;
    String name;

    public static SafeUserResponse from(UserEntity user) {

        return of(user.getUserId(), user.getName());
    }
}
