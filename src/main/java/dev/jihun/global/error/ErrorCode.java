package dev.jihun.global.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    NOT_ALLOWED_METHOD(405, "C002", "Not Allow Method"),
    INTERNAL_SERVER_ERROR(500, "C003", "Server Error"),
    INVALID_REMITTANCE_AMOUNT(400, "C004", "송금액이 바르지 않습니다."),
    INVALID_RECIPIENT_COUNTRY(400, "C005", "수취국가가 잘못되었습니다."),
    EXTERNAL_API_CALL_EXCEPTION(500, "C006", "외부 데이터를 조회하는 도중 문제가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;


}
