package com.github.classpick.user.exception;

import com.github.classpick.global.exception.CustomException;

public class UserException extends CustomException {

    public UserException(UserExceptionCode code) {

        super(code);
    }
}
