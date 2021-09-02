package dev.jihun.domain.exchangerate.exception;

import dev.jihun.global.error.ErrorCode;
import dev.jihun.global.error.exception.BusinessException;

public class InvalidRecipientCountryException extends BusinessException {

    public InvalidRecipientCountryException(String message) {
        super(message, ErrorCode.INVALID_RECIPIENT_COUNTRY);
    }
}
