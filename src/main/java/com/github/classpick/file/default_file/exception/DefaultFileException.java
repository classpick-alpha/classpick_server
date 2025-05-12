package com.github.classpick.file.default_file.exception;

import com.github.classpick.global.exception.CustomException;

public class DefaultFileException extends CustomException {

    public DefaultFileException(DefaultFileExceptionCode code) {

        super(code);
    }
}
