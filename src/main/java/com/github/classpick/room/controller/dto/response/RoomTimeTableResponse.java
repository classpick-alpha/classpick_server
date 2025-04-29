package com.github.classpick.room.controller.dto.response;

import com.github.classpick.reservation.repository.Status;
import com.github.classpick.room.repository.RoomEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RoomTimeTableResponse {
    private long roomId;
    private String roomName;
    private List<DailyReservation> weeklyReservations;

    public static RoomTimeTableResponse from(
            RoomEntity roomEntity,
            List<DailyReservation> weeklyReservations
    ) {
        String roomName = roomEntity.getPlaceName() + " " + roomEntity.getUnitNumber();
        return RoomTimeTableResponse.builder()
                .roomId(roomEntity.getRoomId())
                .roomName(roomName)
                .weeklyReservations(weeklyReservations)
                .build();
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class DailyReservation {
        private LocalDate date;
        private List<TimeReservations> timeReservations;


        @Builder
        public static DailyReservation from(
                LocalDate date,
                List<TimeReservations> timeReservations
        ) {
            return new DailyReservation(date, timeReservations);
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class TimeReservations {
        private LocalTime startTime;
        private LocalTime endTime;
        private Status status;

        public static TimeReservations from(
                LocalTime startTime,
                LocalTime endTime,
                Status status
        ) {
            return new TimeReservations(startTime, endTime, status);
        }
    }
}