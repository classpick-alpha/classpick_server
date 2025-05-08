package com.github.classpick.user.exception;

import com.github.classpick.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionCode implements CustomExceptionCode {

    USER_NOT_FOUND("유저가 존재하지 않습니다.", 4000),
    ;

    private final String message;
    private final int status;
}
