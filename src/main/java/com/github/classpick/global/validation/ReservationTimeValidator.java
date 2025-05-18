package com.github.classpick.global.validation;

import com.github.classpick.reservation.controller.dto.request.CreateReservationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class ReservationTimeValidator implements ConstraintValidator<ReservationTimeValidate, CreateReservationRequest> {

    @Override
    public boolean isValid(CreateReservationRequest request, ConstraintValidatorContext context) {

        if (request == null) return true;

        if (request.getStartTime() == null || request.getEndTime() == null) return true;

        LocalTime startTime = request.getStartTime();
        LocalTime endTime = request.getEndTime();

        return startTime.isBefore(endTime);
    }
}
