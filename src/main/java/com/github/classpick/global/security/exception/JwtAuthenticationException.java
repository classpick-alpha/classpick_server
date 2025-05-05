package com.github.classpick.global.security.exception;

import com.github.classpick.global.exception.CustomException;

public class JwtAuthenticationException extends CustomException {

    public JwtAuthenticationException(JwtAuthenticationExceptionCode code) {

        super(code);
    }
}
