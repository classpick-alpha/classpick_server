package com.github.classpick.reservation.controller;

import com.github.classpick.global.dto.Request;
import com.github.classpick.global.dto.Response;
import com.github.classpick.reservation.controller.dto.request.CreateReservationRequest;
import com.github.classpick.reservation.controller.dto.response.ReservationListResponse;
import com.github.classpick.reservation.controller.dto.response.ReservationResponse;
import com.github.classpick.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "예약")
@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "예약 생성")
    @PostMapping("/v0.0/reservations")
    public Response<ReservationResponse> createReservation(@Valid @RequestBody Request<CreateReservationRequest> body) {

        return Response.ok(reservationService.createReservation(body.getData()));
    }

    @Operation(summary = "예약 취소")
    @DeleteMapping("/v0.0/reservations/{reservationId}")
    public Response<Void> cancelReservation(@PathVariable Long reservationId) {

        reservationService.cancelReservation(reservationId);

        return Response.ok();
    }

    @Operation(summary = "예약 목록")
    @GetMapping("/v0.0/reservations")
    public Response<ReservationListResponse> getReservationsList() {

        return Response.ok(reservationService.getReservationsList());
    }
}
