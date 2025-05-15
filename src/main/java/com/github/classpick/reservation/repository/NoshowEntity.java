package com.github.classpick.reservation.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NoshowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noshowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservation;

    private boolean verified;

    @NotNull
    private String ocrImage;

    public static NoshowEntity of(ReservationEntity reservation, boolean verified, String key) {

        NoshowEntity entity = new NoshowEntity();
        entity.setReservation(reservation);
        entity.setVerified(verified);
        entity.setOcrImage(key);

        return entity;
    }
}
