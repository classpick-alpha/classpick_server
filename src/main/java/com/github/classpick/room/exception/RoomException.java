package com.github.classpick.room.exception;

import com.github.classpick.global.CustomException;

public class RoomException extends CustomException {
    public RoomException(String message, int status) {super(message, status); }
}
