package com.github.classpick.reservation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationExceptionCode {

    RESERVATION_CREATE_FAIL("강의실 예약을 실패했습니다.", 3000),
    RESERVATION_CHANGE_FAIL("강의실 예약 변경을 실패했습니다.", 3001),
    RESERVATION_CANCEL_FAIL("강의실 예약 취소를 실패했습니다.", 3002),
    RESERVATION_NOT_FOUND("강의실 예약 정보가 없습니다.", 3003),
    RESERVATION_ALREADY_EXIST("강의실 예약이 이미 존재합니다.", 3004),
    RESERVATION_NOT_MATCH("강의실 예약 정보가 일치하지않습니다.", 3005);

    private final String message;
    private final int code;
}
