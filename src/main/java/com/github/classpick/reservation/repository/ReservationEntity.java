package com.github.classpick.reservation.repository;

import com.github.classpick.global.entity.BaseTimeEntity;
import com.github.classpick.room.repository.RoomEntity;
import com.github.classpick.user.repository.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    LocalDate date;

    @NotNull
    LocalTime startTime;

    @NotNull
    LocalTime endTime;

    @NotNull
    private String purpose;

    @NotNull
    private Long people;

    private String comment;



}