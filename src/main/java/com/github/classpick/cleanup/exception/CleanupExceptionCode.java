package com.github.classpick.cleanup.exception;

import com.github.classpick.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CleanupExceptionCode implements CustomExceptionCode {
    private final String message;
    private final int status;
}