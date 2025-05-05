package com.github.classpick.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class Request<Data> {

    LocalDateTime timestamp;
    Data data;
}
