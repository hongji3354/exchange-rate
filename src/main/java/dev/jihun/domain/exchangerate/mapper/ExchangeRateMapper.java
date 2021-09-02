package dev.jihun.domain.exchangerate.mapper;

import dev.jihun.domain.exchangerate.domain.ExchangeRate;
import dev.jihun.domain.exchangerate.dto.ExchangeRateInformationResponse;

public class ExchangeRateMapper {

    private ExchangeRateMapper() {
    }

    public static ExchangeRateInformationResponse exchangeRateToExchangeRateInformationResponse(ExchangeRate exchangeRate) {
        return ExchangeRateInformationResponse
            .builder()
            .krw(exchangeRate.getKrw())
            .jpy(exchangeRate.getJpy())
            .php(exchangeRate.getPhp())
            .build();
    }
}
