package com.github.classpick.reservation.service;

import com.github.classpick.global.user.UserGetter;
import com.github.classpick.reservation.controller.dto.request.CancelReservationReq;
import com.github.classpick.reservation.controller.dto.request.CreateReservationReq;
import com.github.classpick.reservation.controller.dto.response.GetReservationListRes;
import com.github.classpick.reservation.exception.ReservationException;
import com.github.classpick.reservation.exception.ReservationExceptionCode;
import com.github.classpick.reservation.repository.ReservationEntity;
import com.github.classpick.reservation.repository.ReservationRepository;
import com.github.classpick.reservation.repository.Status;
import com.github.classpick.room.exception.RoomException;
import com.github.classpick.room.exception.RoomExceptionCode;
import com.github.classpick.room.repository.RoomEntity;
import com.github.classpick.room.repository.RoomRepository;
import com.github.classpick.user.exception.UserException;
import com.github.classpick.user.exception.UserExceptionCode;
import com.github.classpick.user.repository.UserEntity;
import com.github.classpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final UserGetter userGetter;

    @Transactional
    public void createReservation(CreateReservationReq createReservationReq) {

        UserEntity user = userGetter.getUser();

        RoomEntity room = roomRepository.findById(createReservationReq.getRoomId())
                .orElseThrow(() -> new RoomException(RoomExceptionCode.ROOM_NOT_FOUND));

        boolean isDuplicated = reservationRepository.checkAvailableRoom(
                createReservationReq.getRoomId(),
                createReservationReq.getDate(),
                createReservationReq.getStartTime(),
                createReservationReq.getEndTime()
        );
        if (isDuplicated) {
            throw new ReservationException(ReservationExceptionCode.RESERVATION_ALREADY_EXIST);
        }

        ReservationEntity reservation = ReservationEntity.builder()
                .user(user)
                .room(room)
                .purpose(createReservationReq.getPurpose())
                .people(createReservationReq.getPeople())
                .startTime(createReservationReq.getStartTime())
                .endTime(createReservationReq.getEndTime())
                .comment(createReservationReq.getComment())
                .status(Status.REQUESTED)
                .build();

        reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(CancelReservationReq cancelReservationReq) {

        ReservationEntity reservation = reservationRepository.findById(cancelReservationReq.getReservationId())
                .orElseThrow(() -> new ReservationException(ReservationExceptionCode.RESERVATION_NOT_FOUND));

        if (!reservation.getUser().getUserId().equals(cancelReservationReq.getUserId())) {
            throw new ReservationException(ReservationExceptionCode.RESERVATION_NOT_MATCH);
        }

        reservationRepository.delete(reservation);
    }

    @Transactional(readOnly = true)
    public List<GetReservationListRes> getReservationsList(Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        return reservationRepository.findByUser_UserId(userId).stream().map(GetReservationListRes::fromEntity).toList();
    }
}
