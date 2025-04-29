package com.github.classpick.defaultfile.exception;

import com.github.classpick.global.exception.CustomException;

public class DefaultFileException extends CustomException {
    public DefaultFileException(String message, int status) {
        super(message, status);
    }
}
