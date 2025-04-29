package com.github.classpick.reservation.controller;

import com.github.classpick.global.Request;
import com.github.classpick.global.Response;
import com.github.classpick.reservation.controller.dto.request.CancelReservationReq;
import com.github.classpick.reservation.controller.dto.request.CreateReservationReq;
import com.github.classpick.reservation.controller.dto.response.GetReservationListRes;
import com.github.classpick.reservation.repository.ReservationRepository;
import com.github.classpick.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @Operation(summary = "예약 생성", description = "사용자가 강의실 예약을 생성합니다.")
    @PostMapping("/v0.0/reservations")
    public Response<Void> createReservation(@Valid @RequestBody Request<CreateReservationReq> createReservationReq) {
        reservationService.createReservation(createReservationReq.getData());
        return Response.of(200, "success", null);
    }

    @Operation(summary = "예약 취소", description = "사용자가 강의실 예약을 취소합니다.")
    @DeleteMapping("/v0.0/reservations/{reservationId}")
    public Response<Void> cancelReservation(@PathVariable Long reservationId, @RequestParam Long userId) {
        reservationService.cancelReservation(CancelReservationReq.of(reservationId, userId));
        return Response.of(200, "success", null);
    }

    @Operation(summary = "예약 목록", description = "강의실 예약 목록을 가져옵니다.")
    @GetMapping("/v0.0/reservations")
    public Response<List<GetReservationListRes>> getReservationsList(@RequestParam Long userId) {
        List<GetReservationListRes> getReservationListRes = reservationService.getReservationsList(userId);
        return Response.of(200, "success", getReservationListRes);
    }


}
