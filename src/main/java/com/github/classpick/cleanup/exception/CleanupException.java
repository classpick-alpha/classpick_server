package com.github.classpick.cleanup.exception;

import com.github.classpick.global.exception.CustomException;
import com.github.classpick.global.exception.CustomExceptionCode;

public class CleanupException extends CustomException{

    public CleanupException(CustomExceptionCode code) {

        super(code);
    }
}
