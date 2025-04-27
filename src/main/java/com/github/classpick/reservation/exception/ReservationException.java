package com.github.classpick.reservation.exception;

import com.github.classpick.global.CustomException;

public class ReservationException extends CustomException {
    public ReservationException(String message, int status) {
        super(message, status);
    }
}
