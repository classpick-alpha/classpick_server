package com.github.classpick.security.exception;

import lombok.Getter;

@Getter
public enum JwtAuthenticationExceptionCode {

    TOKEN_MISSING("토큰이 존재하지 않습니다.", 1000),
    INVALID_TOKEN("유효하지 않은 토큰입니다.", 1001);

    private final String message;
    private final int code;

    JwtAuthenticationExceptionCode(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
