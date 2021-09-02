package dev.jihun.domain.exchangerate.api;

import dev.jihun.domain.exchangerate.application.ExchangeRateApplication;
import dev.jihun.domain.exchangerate.dto.ExchangeRateInformationResponse;
import dev.jihun.domain.exchangerate.dto.ReceivedAmountCalculationRequest;
import dev.jihun.domain.exchangerate.dto.ReceivedAmountCalculationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeRateApi {

    private final ExchangeRateApplication exchangeRateApplication;

    @GetMapping("/exchange-rate")
    public ResponseEntity<ExchangeRateInformationResponse> exchangeRateInformation() {
        return ResponseEntity.ok(exchangeRateApplication.getExchangeRateInformation());
    }

    @PostMapping("/receive-amount/calculation")
    public ResponseEntity<ReceivedAmountCalculationResponse> receivedAmountCalculation(@RequestBody ReceivedAmountCalculationRequest request) {
        return ResponseEntity.ok(exchangeRateApplication.receivedAmountCalculation(request));
    }

}
