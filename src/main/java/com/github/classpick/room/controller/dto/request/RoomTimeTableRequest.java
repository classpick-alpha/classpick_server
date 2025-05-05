package com.github.classpick.room.controller.dto.request;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record RoomTimeTableRequest(

        @Nullable
        LocalDate date
) {

}