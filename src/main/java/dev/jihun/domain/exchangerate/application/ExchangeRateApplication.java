package dev.jihun.domain.exchangerate.application;

import dev.jihun.domain.exchangerate.dto.ExchangeRateInformationResponse;
import dev.jihun.domain.exchangerate.mapper.ExchangeRateMapper;
import dev.jihun.infra.exchangerate.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateApplication {

    private final ExchangeRate exchangeRate;

    public ExchangeRateInformationResponse getExchangeRateInformation() {
        return ExchangeRateMapper.exchangeRateToExchangeRateInformationResponse(this.exchangeRate.exchangeRateInquiry());
    }

}
