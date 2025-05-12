package com.github.classpick.reservation.service;

import com.github.classpick.file.upload.dto.UploadImageResponse;
import com.github.classpick.file.upload.service.UploadService;
import com.github.classpick.global.user.UserGetter;
import com.github.classpick.reservation.controller.dto.request.CreateReservationRequest;
import com.github.classpick.reservation.controller.dto.response.ReservationListResponse;
import com.github.classpick.reservation.controller.dto.response.ReservationResponse;
import com.github.classpick.reservation.exception.ReservationException;
import com.github.classpick.reservation.exception.ReservationExceptionCode;
import com.github.classpick.reservation.repository.ProofType;
import com.github.classpick.reservation.repository.ReservationEntity;
import com.github.classpick.reservation.repository.ReservationRepository;
import com.github.classpick.reservation.repository.Status;
import com.github.classpick.room.exception.RoomException;
import com.github.classpick.room.exception.RoomExceptionCode;
import com.github.classpick.room.repository.RoomEntity;
import com.github.classpick.room.repository.RoomRepository;
import com.github.classpick.user.repository.UserEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    private final UserGetter userGetter;
    private final UploadService uploadService;

    @Transactional
    public ReservationResponse createReservation(long roomId, CreateReservationRequest dto) {

        UserEntity user = userGetter.getUser();

        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException(RoomExceptionCode.ROOM_NOT_FOUND));

        if (reservationRepository.checkAvailableRoom(roomId, dto.getDate(), dto.getStartTime(), dto.getEndTime())) {

            throw new ReservationException(ReservationExceptionCode.RESERVATION_ALREADY_EXIST);
        }

        ReservationEntity reservation = ReservationEntity.builder()
                .user(user)
                .room(room)
                .purpose(dto.getPurpose())
                .people(dto.getPeople())
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .comment(dto.getComment())
                .status(Status.REQUESTED)
                .build();

        reservation = reservationRepository.save(reservation);

        return ReservationResponse.from(reservation);
    }

    @Transactional
    public void cancelReservation(long reservationId) {

        UserEntity user = userGetter.getUser();

        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationExceptionCode.RESERVATION_NOT_FOUND));

        if (!reservation.getUser().getUserId().equals(user.getUserId())) {

            throw new ReservationException(ReservationExceptionCode.RESERVATION_NOT_MATCH);
        }

        reservationRepository.delete(reservation);
    }

    @Transactional(readOnly = true)
    public ReservationListResponse getReservationsList() {

        UserEntity user = userGetter.getUser();

        List<ReservationResponse> reservations = reservationRepository.findByUser_UserId(user.getUserId())
                .stream()
                .map(ReservationResponse::from)
                .toList();

        return ReservationListResponse.of(reservations);
    }

    public UploadImageResponse generateProofUploadUrl(Long reservationId, ProofType type) {
        return uploadService.uploadImage("reservations", reservationId, "proofs/" + type.toPathname());
    }
}
