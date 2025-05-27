package com.github.classpick.room.exception;

import com.github.classpick.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomExceptionCode implements CustomExceptionCode {

    ROOM_NOT_FOUND("강의실 정보가 없습니다.", 2000),
    ROOM_EXCEL_PLACENAME_MISSING("엑셀의 'placeName' 값이 누락되었습니다.", 2001),
    ROOM_EXCEL_UNITNUMBER_MISSING("엑셀의 'unitNumber' 값이 누락되었습니다.", 2002),
    ROOM_EXCEL_PARSING_ERROR("엑셀 파일 파싱 중 오류가 발생했습니다.", 2003),
    ROOM_EXCEL_EMPTY_FILE("업로드된 파일이 비어 있습니다.", 2004),
    ROOM_EXCEL_UNSUPPORTED_FORMAT("엑셀(.xlsx) 파일만 업로드할 수 있습니다.", 2005),
    ROOM_EXCEL_IO_ERROR("엑셀 파일을 읽는 중 에러가 발생했습니다.", 2006),
    ROOM_EXCEL_INVALID_TYPE("", 2007),

    ;

    private final String message;
    private final int status;
}
