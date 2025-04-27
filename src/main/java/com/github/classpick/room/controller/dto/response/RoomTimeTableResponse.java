package com.github.classpick.room.controller.dto.response;

import com.github.classpick.room.repository.RoomEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;

import com.github.classpick.reservation.repository.Status;

@Builder
public record RoomTimeTableResponse(
    long roomId,
    String roomName,
    List<DailyReservation> weeklyReservations
) {
    public static RoomTimeTableResponse from(
            RoomEntity roomEntity,
            List<DailyReservation> weeklyReservations
    ) {
        String roomName = roomEntity.getPlaceName() + " " + roomEntity.getRoomId();
        return RoomTimeTableResponse.builder()
                .roomId(roomEntity.getRoomId())
                .roomName(roomName)
                .weeklyReservations(weeklyReservations)
                .build();
    }

    @Builder
    public record DailyReservation(
            LocalDate date,
            List<TimeReservations> timeReservations
    ){
        @Builder
        public static DailyReservation from(
                LocalDate date,
                List<TimeReservations> timeReservations
        ){
            return new DailyReservation(date, timeReservations);
        }

    }

    @Builder
    public record TimeReservations(
            LocalTime startTime,
            LocalTime endTime,
            Status status
    ){
        public static TimeReservations from(
                LocalTime startTime,
                LocalTime endTime,
                Status status
        ){
            return new TimeReservations(startTime, endTime, status);
        }
    }
}