package com.github.classpick.reservation.controller.dto.response;

import com.github.classpick.reservation.repository.ReservationEntity;
import com.github.classpick.reservation.repository.Status;
import com.github.classpick.room.repository.RoomEntity;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetReservationListRes {

    //예약 정보
    @NotNull
    private Long reservationId;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    private Long people;
    @NotNull
    private String purpose;
    @NotNull
    private Status status;

    //강의실 정보
    @NotNull
    private Integer unitNumber;
    @NotNull
    private String placeName;
    @NotNull
    private Integer capacity;

    public static GetReservationListRes fromEntity(ReservationEntity reservation) {
        RoomEntity room = reservation.getRoom();
        return GetReservationListRes.builder()
                .reservationId(reservation.getReservationId())
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .people(reservation.getPeople())
                .purpose(reservation.getPurpose())
                .unitNumber(room.getUnitNumber())
                .placeName(room.getPlaceName())
                .capacity(room.getCapacity())
                .build();
    }
}
