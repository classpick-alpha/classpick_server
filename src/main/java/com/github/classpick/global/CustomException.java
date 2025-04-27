package com.github.classpick.global;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int status;

    protected CustomException(String message, int status) {
        super(message);
        this.status = status;
    }
}
