package com.github.classpick.reservation.service;

import com.github.classpick.file.upload.dto.UploadImageResponse;
import com.github.classpick.file.upload.util.S3KeyFactory;
import com.github.classpick.global.external.aws.s3.S3Service;
import com.github.classpick.global.user.UserGetter;
import com.github.classpick.reservation.controller.dto.request.CreateReservationRequest;
import com.github.classpick.reservation.controller.dto.request.OcrRequest;
import com.github.classpick.reservation.controller.dto.response.OcrResponse;
import com.github.classpick.reservation.controller.dto.response.ReservationListResponse;
import com.github.classpick.reservation.controller.dto.response.ReservationResponse;
import com.github.classpick.reservation.exception.ReservationException;
import com.github.classpick.reservation.exception.ReservationExceptionCode;
import com.github.classpick.reservation.repository.NoshowEntity;
import com.github.classpick.reservation.repository.NoshowRepository;
import com.github.classpick.reservation.repository.ReservationEntity;
import com.github.classpick.reservation.repository.ReservationRepository;
import com.github.classpick.reservation.repository.Status;
import com.github.classpick.room.exception.RoomException;
import com.github.classpick.room.exception.RoomExceptionCode;
import com.github.classpick.room.repository.RoomEntity;
import com.github.classpick.room.repository.RoomRepository;
import com.github.classpick.user.repository.UserEntity;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final NoshowRepository noshowRepository;

    private final S3Service s3Service;
    private final UserGetter userGetter;

    @Value("${app.ocr.apikey}")
    private String ocrApiKey;

    @Transactional(readOnly = true)
    public ReservationListResponse getReservationsList() {

        UserEntity user = userGetter.getUser();

        List<ReservationResponse> reservations = reservationRepository.findByUser_UserId(user.getUserId())
                .stream()
                .map(reservationEntity -> {
                    NoshowEntity noshowEntity = noshowRepository.findByVerifiedNoshow(reservationEntity)
                            .orElse(NoshowEntity.of(null, false, null));

                    return ReservationResponse.from(reservationEntity, noshowEntity.isVerified());
                })
                .toList();

        return ReservationListResponse.of(reservations);
    }

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

    public UploadImageResponse generateOcrImage(Long reservationId) {

        reservationRepository.findById(reservationId).stream().peek((reservation) -> {
            if (!reservation.getUser().equals(userGetter.getUser())) {
                throw new ReservationException(ReservationExceptionCode.RESERVATION_USER_NOT_MATCH);
            }
        }).findFirst().orElseThrow(() -> new ReservationException(ReservationExceptionCode.RESERVATION_NOT_FOUND));

        return UploadImageResponse.of(s3Service.generatePresignedUrl(S3KeyFactory.reservatioOcrKey(reservationId)));
    }

    @Transactional
    public OcrResponse verifyOcr(long reservationId, OcrRequest request) {

        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationExceptionCode.RESERVATION_NOT_FOUND));

        String unitNumber = reservation.getRoom().getUnitNumber();

        HttpResponse<JsonNode> response = Unirest.post("https://ocr.classpick.co.kr")
                .header("Content-Type", "image/jpeg")
                .header("X-CLASSPICK-VERIFICATION-KEY", ocrApiKey)
                .body(Unirest.get(request.getImageUrl()).asBytes().getBody())
                .asJson();

        if (response.getBody().getObject().getInt("status") != 200) {
            throw new ReservationException(
                    response.getBody().getObject().getString("message"),
                    ReservationExceptionCode.RESERVATION_OCR_VERIFY_FAILED.getStatus()
            );
        }

        //noinspection unchecked
        boolean result = ((List<JSONObject>) response.getBody().getObject().getJSONArray("data").toList()).stream()
                .filter(json -> json.getDouble("score") >= 0.9)
                .anyMatch(json -> json.getString("text").equals(unitNumber));

        noshowRepository.save(NoshowEntity.of(reservation, result, request.getImageUrl()));

        return OcrResponse.of(result);
    }

    public UploadImageResponse generateCleanUpImage(Long reservationId) {

        reservationRepository.findById(reservationId).stream().peek((reservation) -> {
            if (!reservation.getUser().equals(userGetter.getUser())) {
                throw new ReservationException(ReservationExceptionCode.RESERVATION_USER_NOT_MATCH);
            }
        }).findFirst().orElseThrow(() -> new ReservationException(ReservationExceptionCode.RESERVATION_NOT_FOUND));
        return UploadImageResponse.of(s3Service.generatePresignedUrl(S3KeyFactory.reservatioCleanUpKey(reservationId)));
    }
}
