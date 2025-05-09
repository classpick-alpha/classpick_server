package com.github.classpick.room.controller.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class RoomFilterRequest {

    @Nullable
    String placeName;

    @Nullable
    @Min(1)
    Integer capacity;

    @Nullable
    LocalDate date;

    @Nullable
    LocalTime startTime;

    @Nullable
    LocalTime endTime;
}
