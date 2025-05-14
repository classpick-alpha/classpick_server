package com.github.classpick.lecture.exception;

import com.github.classpick.global.exception.CustomException;

public class LectureException extends CustomException {

    public LectureException(LectureExceptionCode code) {

        super(code);
    }
}
