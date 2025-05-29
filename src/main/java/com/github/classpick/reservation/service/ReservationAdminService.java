package com.github.classpick.reservation.service;

import com.github.classpick.reservation.controller.dto.response.*;
import com.github.classpick.reservation.exception.ReservationException;
import com.github.classpick.reservation.exception.ReservationExceptionCode;
import com.github.classpick.reservation.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationAdminService {

    private final ReservationRepository reservationRepository;
    private final NoshowRepository noshowRepository;

    @Transactional(readOnly = true)
    public UserReservationListResponse getUserReservationsList() {

        List<UserReservationResponse> userReservationResponses = reservationRepository.findAll()
                .stream()
                .map(UserReservationResponse::from)
                .toList();

        return UserReservationListResponse.of(userReservationResponses);
    }

    @Transactional
    public void approveReservation(long reservationId) {

        ReservationEntity reservation = getPendingReservations(reservationId);

        reservation.setStatus(Status.APPROVED);
    }

    @Transactional
    public void rejectReservation(long reservationId) {

        ReservationEntity reservation = getPendingReservations(reservationId);

        reservation.setStatus(Status.REJECTED);
    }

    @Transactional
    public ReservationEntity getPendingReservations(long reservationId) {

        return reservationRepository.findById(reservationId).stream().peek(reservationEntity -> {
            if (reservationEntity.getStatus() == Status.APPROVED) {
                throw new ReservationException(ReservationExceptionCode.RESERVATION_ALREADY_APPROVED);
            }
        }).peek(reservationEntity -> {
            if (reservationEntity.getStatus() == Status.REJECTED) {
                throw new ReservationException(ReservationExceptionCode.RESERVATION_ALREADY_REJECTED);
            }
        }).findAny().orElseThrow(() -> new ReservationException(ReservationExceptionCode.RESERVATION_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public NoshowListResponse getNoshowList() {

        List<NoshowResponse> noshowResponses = noshowRepository.findAll()
                .stream()
                .map(NoshowResponse::from)
                .toList();

        return NoshowListResponse.of(noshowResponses);
    }
}
