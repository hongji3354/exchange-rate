package dev.jihun.domain.exchangerate.exception;

import dev.jihun.global.error.ErrorCode;
import dev.jihun.global.error.exception.BusinessException;

public class InvalidRemittanceAmountException extends BusinessException {

    public InvalidRemittanceAmountException(String message) {
        super(message, ErrorCode.INVALID_REMITTANCE_AMOUNT);
    }
}
