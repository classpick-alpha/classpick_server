package com.github.classpick.reservation.repository;

import com.github.classpick.global.entity.BaseTimeEntity;
import com.github.classpick.room.repository.RoomEntity;
import com.github.classpick.user.repository.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity extends BaseTimeEntity {

    @NotNull
    LocalDate date;
    @NotNull
    LocalTime startTime;
    @NotNull
    LocalTime endTime;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
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
    private String purpose;

    @NotNull
    private Long people;

    private String comment;


}