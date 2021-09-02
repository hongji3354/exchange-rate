package dev.jihun.domain.exchangerate.api;

import dev.jihun.domain.exchangerate.application.ExchangeRateApplication;
import dev.jihun.domain.exchangerate.dto.ExchangeRateInformationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeRateApi {

    private final ExchangeRateApplication exchangeRateApplication;

    public ResponseEntity<ExchangeRateInformationResponse> exchangeRateInformation() {
        return ResponseEntity.ok(exchangeRateApplication.getExchangeRateInformation());
    }

}
