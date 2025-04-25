package com.github.classpick.defaultfile.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DefaultFileExceptionCode {

    FILE_SAVE_FAIL("파일을 저장할 수 없습니다", 5000),
    FILE_NOT_FOUND("파일을 찾을 수 없습니다.", 5001),
    FILE_DELETE_FAIL("파일 삭제를 실패했습니다.", 5002);

    private final String message;
    private final int code;

}
