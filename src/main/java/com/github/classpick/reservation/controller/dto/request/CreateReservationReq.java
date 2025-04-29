package com.github.classpick.reservation.controller.dto.request;

import com.github.classpick.reservation.repository.Status;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class CreateReservationReq {

    @NotNull
    private Long userId;

    @NotNull
    private Long roomId;

    @NotNull
    private String purpose;

    @NotNull
    private Long people;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    private Status status;

    private String comment;
}
