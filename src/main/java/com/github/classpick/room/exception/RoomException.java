package com.github.classpick.room.exception;

import com.github.classpick.global.exception.CustomException;

public class RoomException extends CustomException {
    public RoomException(String message, int code) {
        super(message, code);
    }
}
