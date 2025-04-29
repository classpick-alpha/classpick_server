package com.github.classpick.user.service;

import com.github.classpick.user.controller.dto.UpdateUserInfoReq;
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

    public Long getUserInfo(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND.getMessage(),
                        UserExceptionCode.USER_NOT_FOUND.getCode()));
        return userEntity.getUserId();
    }

    @Transactional
    public Long updateUserInfo(Long userId, UpdateUserInfoReq request) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND.getMessage(),
                        UserExceptionCode.USER_NOT_FOUND.getCode())
                );

        userEntity.updateUserInfo(
                request.getUserGroup(),
                request.getSchoolNumber(),
                request.getPhoneNumber()
        );

        return userEntity.getUserId();
    }
}
