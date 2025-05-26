package com.github.classpick.reservation.exception;

import com.github.classpick.global.exception.CustomException;

public class ReservationException extends CustomException {

    public ReservationException(ReservationExceptionCode code) {

        super(code);
    }

    public ReservationException(String message, int status) {

        super(message, status);
    }
}
