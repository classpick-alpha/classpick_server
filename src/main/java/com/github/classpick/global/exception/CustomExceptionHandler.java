package com.github.classpick.global.exception;

import com.github.classpick.global.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public Response <Void> handleCustomException(CustomException e) {
        return Response.of(e.getStatus(), e.getMessage(), null);
    }
}