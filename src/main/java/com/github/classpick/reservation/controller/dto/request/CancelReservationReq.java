package com.github.classpick.reservation.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class CancelReservationReq {

    @NotNull
    private Long userId;

    @NotNull
    private Long reservationId;
}
