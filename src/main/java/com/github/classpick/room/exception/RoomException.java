package com.github.classpick.room.exception;

import com.github.classpick.global.CustomException;

public class RoomException extends CustomException {
    public RoomException(RoomExceptions roomExceptions) {
        super(roomExceptions.getMessage(), roomExceptions.getCode());
    }

    public static RoomException of(RoomExceptions roomExceptions) {
        return new RoomException(roomExceptions);
    }
}
