package com.github.classpick.room.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomExceptionCode {

    ROOM_NOT_FOUND("강의실 정보가 없습니다.", 2000);

    private final String message;
    private final int code;
}
