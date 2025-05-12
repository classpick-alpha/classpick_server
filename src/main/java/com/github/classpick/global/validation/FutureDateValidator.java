package com.github.classpick.global.validation;

import com.github.classpick.reservation.controller.dto.request.CreateReservationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class FutureDateValidator implements ConstraintValidator<FutureDateValidate, CreateReservationRequest> {

    @Override
    public boolean isValid(CreateReservationRequest request, ConstraintValidatorContext context) {

        if (request == null) return true;

        if (request.getDate() == null || request.getStartTime() == null) return true;

        LocalDateTime reservationTime = LocalDateTime.of(request.getDate(), request.getStartTime());
        LocalDateTime now = LocalDateTime.now();

        return !reservationTime.isBefore(now);
    }
}
