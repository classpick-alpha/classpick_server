package com.github.classpick.reservation.controller.dto.request;

import com.github.classpick.reservation.repository.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

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
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private Status status;

    private String comment;
}
