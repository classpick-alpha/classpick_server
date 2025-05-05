package com.github.classpick.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final int status;

    protected CustomException(CustomExceptionCode code) {

        this(code.getMessage(), code.getStatus());
    }

    public CustomException(String message, int status) {

        super(message);
        this.status = status;
    }
}
