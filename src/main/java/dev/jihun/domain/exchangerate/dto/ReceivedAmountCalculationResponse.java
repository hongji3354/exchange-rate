package dev.jihun.domain.exchangerate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReceivedAmountCalculationResponse {

    private String exchangeRate;
    private String receivedAmount;
}
