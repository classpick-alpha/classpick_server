package com.github.classpick.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(staticName = "of")
@Getter
public class Request<Data> {

    private LocalDateTime timestamp;
    private Data data;
}
