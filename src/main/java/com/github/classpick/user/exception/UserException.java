package com.github.classpick.user.exception;

import com.github.classpick.global.CustomException;

public class UserException extends CustomException {
    public UserException(String message, int status) {
        super(message, status);
    }
}
