package com.github.classpick.global.exception;

import com.github.classpick.global.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({ NoResourceFoundException.class, HttpRequestMethodNotSupportedException.class})
    public Response<?> noResourceFoundException(Exception ignored) {

        return Response.error(new CustomException("요청하신 리소스를 찾을 수 없습니다.", 9001));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class
    })
    public Response<?> httpMessageNotReadableException(Exception ignored) {

        return Response.error(new CustomException("요청 데이터가 올바르지 않습니다.", 9002));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public Response<?> authorizationDeniedException(AuthorizationDeniedException ignored) {

        return Response.error(new CustomException("권한이 없습니다.", 9003));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        if (Objects.nonNull(e.getBindingResult().getFieldError())) {

            String field = e.getBindingResult().getFieldError().getField();
            String message = e.getBindingResult().getFieldError().getDefaultMessage();
            String errorMessage = String.format("%s은(는) %s", field, message);

            return Response.error(new CustomException(errorMessage, 9004));
        }

        if (Objects.nonNull(e.getBindingResult().getGlobalError())) {

            String errorMessage = e.getBindingResult().getGlobalError().getDefaultMessage();

            return Response.error(new CustomException(errorMessage, 9004));
        }

        return Response.error(new CustomException(e.getMessage(), 9004));
    }

    @ExceptionHandler(CustomException.class)
    public Response<Void> handleCustomException(CustomException exception) {

        return Response.error(exception);
    }

    @ExceptionHandler(Exception.class)
    public Response<?> exception(Exception e) {

        log.error("", e);

        return Response.error(new CustomException("알 수 없는 오류가 발생했습니다.", 9005));
    }
}