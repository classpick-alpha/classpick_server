package com.github.classpick.user.service;

import com.github.classpick.global.user.UserGetter;
import com.github.classpick.user.controller.dto.request.UpdateUserRequest;
import com.github.classpick.user.controller.dto.response.SafeUserResponse;
import com.github.classpick.user.controller.dto.response.UserResponse;
import com.github.classpick.user.exception.UserException;
import com.github.classpick.user.exception.UserExceptionCode;
import com.github.classpick.user.repository.UserEntity;
import com.github.classpick.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserGetter userGetter;

    public UserResponse getMe() {

        return UserResponse.from(userGetter.getUser());
    }

    public SafeUserResponse getUserInfo(Long userId) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        return SafeUserResponse.from(userEntity);
    }

    @Transactional
    public UserResponse updateUserInfo(UpdateUserRequest request) {

        UserEntity userEntity = userGetter.getUser();

        userEntity.setUserGroup(request.getUserGroup());
        userEntity.setSchoolNumber(request.getSchoolNumber());
        userEntity.setPhoneNumber(request.getPhoneNumber());

        return UserResponse.from(userEntity);
    }
}
