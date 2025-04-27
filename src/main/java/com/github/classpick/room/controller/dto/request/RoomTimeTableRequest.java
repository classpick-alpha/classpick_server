package com.github.classpick.room.controller.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record RoomTimeTableRequest(

        @NotNull
        Long roomId,

        @Nullable
        LocalDate date
) {
}