package com.github.classpick.reservation.controller.dto.request;

import com.github.classpick.global.validation.FutureDateValidate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor(staticName = "of")
@FutureDateValidate
public class CreateReservationRequest {

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @Min(1)
    private int people;

    @NotBlank
    private String purpose;

    private String comment;
}
