package com.github.classpick.room.controller.dto.response;

import com.github.classpick.reservation.repository.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Getter
@AllArgsConstructor(staticName = "of")
public class RoomTimeTableResponse {

    RoomResponse room;
    Collection<DailyReservation> weekly;

    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class DailyReservation {

        LocalDate date;
        Collection<TimeReservations> reservations;
    }

    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class TimeReservations {

        LocalTime startTime;
        LocalTime endTime;
        Status status;
    }
}