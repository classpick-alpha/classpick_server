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

    Integer people;

    String purpose;

    Status status;

    public static ReservationResponse from(ReservationEntity reservation) {

        return of(
                RoomResponse.from(reservation.getRoom()),
                reservation.getReservationId(),
                reservation.getDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getPeople(),
                reservation.getPurpose(),
                reservation.getStatus()
        );
    }
}
