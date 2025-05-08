package com.github.classpick.global.dto;

import com.github.classpick.global.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class Response<Data> {

    private int status;
    private String message;
    private LocalDateTime timestamp;
    private Data data;

    public static <Data> Response<Data> ok() {

        return ok(null);
    }

    public static <Data> Response<Data> ok(Data data) {

        return of(200, "success", data);
    }

    public static <Data> Response<Data> error(CustomException exception) {

        return of(exception.getStatus(), exception.getMessage());
    }

    private static <Data> Response<Data> of(int status, String message) {

        return of(status, message, null);
    }

    private static <Data> Response<Data> of(int status, String message, Data data) {

        return new Response<>(status, message, LocalDateTime.now(), data);
    }
}
