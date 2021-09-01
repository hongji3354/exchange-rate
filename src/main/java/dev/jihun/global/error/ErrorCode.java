package dev.jihun.global.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    NOT_ALLOWED_METHOD(405, "C002", "Not Allow Method"),
    INTERNAL_SERVER_ERROR(500, "C003", "Server Error");

    private final int status;
    private final String code;
    private final String message;


}
