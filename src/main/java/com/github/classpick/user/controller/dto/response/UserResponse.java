package com.github.classpick.user.controller.dto.response;

import com.github.classpick.user.repository.Role;
import com.github.classpick.user.repository.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserResponse {

    long userId;
    String name;
    Role role;
    String userGroup;
    String schoolNumber;
    String email;
    String phoneNumber;

    public static UserResponse from(UserEntity user) {

        return of(
                user.getUserId(),
                user.getName(),
                user.getRole(),
                user.getUserGroup(),
                user.getSchoolNumber(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
