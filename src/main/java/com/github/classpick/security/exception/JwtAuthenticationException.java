package com.github.classpick.security.exception;

import com.github.classpick.global.CustomException;

public class JwtAuthenticationException extends CustomException {
    public JwtAuthenticationException(String message, int status) {
        super(message, status);
    }
}
