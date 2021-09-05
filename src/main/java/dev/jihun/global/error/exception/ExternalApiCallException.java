package dev.jihun.global.error.exception;

import dev.jihun.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class ExternalApiCallException extends RuntimeException {

    private final ErrorCode errorCode;

    public ExternalApiCallException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ExternalApiCallException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
