package com.github.classpick.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(staticName = "of")
@Getter
public class Response<Data> {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;
    private Data data;

    public static <Data> Response<Data> of(Integer status, String message, Data data) {
        return new Response<>(status, message, LocalDateTime.now(), data);
    }

    public static <Data> Response<Data> ok(Data data) {
        return of(200, "sucess", data);
    }
}
