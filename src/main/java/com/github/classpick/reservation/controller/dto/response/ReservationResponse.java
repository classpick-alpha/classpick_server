package com.github.classpick.reservation.controller.dto.response;

import com.github.classpick.reservation.repository.ReservationEntity;
import com.github.classpick.reservation.repository.Status;
import com.github.classpick.room.controller.dto.response.RoomResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class ReservationResponse {

    RoomResponse room;

    long reservationId;

    LocalDate date;

    LocalTime startTime;

    LocalTime endTime;

    int people;

    String purpose;

    Status status;

    boolean ocrVerified;

    public static ReservationResponse from(ReservationEntity reservation) {

        return from(reservation, false);
    }

    public static ReservationResponse from(ReservationEntity reservation, boolean ocrVerified) {

        return of(
                RoomResponse.from(reservation.getRoom()),
                reservation.getReservationId(),
                reservation.getDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getPeople(),
                reservation.getPurpose(),
                reservation.getStatus(),
                ocrVerified
        );
    }
}
