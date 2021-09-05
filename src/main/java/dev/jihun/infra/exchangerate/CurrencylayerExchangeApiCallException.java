package dev.jihun.infra.exchangerate;

import dev.jihun.global.error.ErrorCode;
import dev.jihun.global.error.exception.ExternalApiCallException;

public class CurrencylayerExchangeApiCallException extends ExternalApiCallException {

    public CurrencylayerExchangeApiCallException(ErrorCode errorCode) {
        super(errorCode);
    }
}
