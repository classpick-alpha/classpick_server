package com.github.classpick.lecture.exception;

import com.github.classpick.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LectureExceptionCode implements CustomExceptionCode {

    ;

    private final String message;
    private final int status;
}
