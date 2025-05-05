package com.github.classpick.global.security.exception;

import com.github.classpick.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtAuthenticationExceptionCode implements CustomExceptionCode {

    TOKEN_MISSING("토큰이 존재하지 않습니다.", 1000),
    INVALID_TOKEN("유효하지 않은 토큰입니다.", 1001);

    private final String message;
    private final int status;
}
