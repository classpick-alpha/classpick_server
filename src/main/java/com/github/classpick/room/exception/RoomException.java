package com.github.classpick.room.exception;

import com.github.classpick.global.exception.CustomException;

public class RoomException extends CustomException {

    public RoomException(RoomExceptionCode code) {

        super(code);
    }

    public RoomException(String message, int status) {

        super(message, status);
    }
}
