package com.github.classpick.reservation.service;

import com.github.classpick.reservation.exception.ReservationException;
import com.github.classpick.reservation.exception.ReservationExceptionCode;
import com.github.classpick.reservation.repository.ReservationEntity;
import com.github.classpick.reservation.repository.ReservationRepository;
import com.github.classpick.reservation.repository.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationAdminService {
    private final ReservationRepository reservationRepository;

    @Transactional
    public void approveReservation(long reservationId){
        ReservationEntity reservation = getPendingReservations(reservationId);

        reservation.setStatus(Status.APPROVED);
        reservationRepository.save(reservation);
    }

    @Transactional
    public void rejectReservation(long reservationId) {
        ReservationEntity reservation = getPendingReservations(reservationId);

        reservation.setStatus(Status.REJECTED);
        reservationRepository.save(reservation);
    }

    @Transactional
    public ReservationEntity getPendingReservations(long reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationExceptionCode.RESERVATION_NOT_FOUND));

        if (reservation.getStatus() == Status.APPROVED) {
            throw new ReservationException(ReservationExceptionCode.RESERVATION_ALREADY_APPROVED);
        }

        if (reservation.getStatus() == Status.REJECTED) {
            throw new ReservationException(ReservationExceptionCode.RESERVATION_ALREADY_REJECTED);
        }

        return reservation;
    }
}
