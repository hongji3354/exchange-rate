package dev.jihun.domain.exchangerate.application;

import dev.jihun.domain.exchangerate.domain.ExchangeRate;
import dev.jihun.domain.exchangerate.dto.ExchangeRateInformationResponse;
import dev.jihun.domain.exchangerate.dto.ReceivedAmountCalculationRequest;
import dev.jihun.domain.exchangerate.dto.ReceivedAmountCalculationResponse;
import dev.jihun.domain.exchangerate.mapper.ExchangeRateMapper;
import dev.jihun.infra.exchangerate.ExchangeRateApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateApplication {

    private final ExchangeRateApi exchangeRateApi;

    public ExchangeRateInformationResponse getExchangeRateInformation() {
        return ExchangeRateMapper.exchangeRateToExchangeRateInformationResponse(this.exchangeRateApi.exchangeRateInquiry());
    }

    public ReceivedAmountCalculationResponse receivedAmountCalculation(ReceivedAmountCalculationRequest request) {
        ExchangeRate exchangeRate = new ExchangeRate();
        String receiveAmount = exchangeRate.receiveAmountCalculation(request.getRemittanceAmount(), request.getReceiveCountry());
        return new ReceivedAmountCalculationResponse(receiveAmount);
    }
}
