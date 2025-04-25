package com.github.classpick.user.exception;

import lombok.Getter;

@Getter
public enum UserExceptionCode {

    USER_NOT_FOUND("유저가 존재하지 않습니다.", 4000);

    private final String message;
    private final int code;

    UserExceptionCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

}
