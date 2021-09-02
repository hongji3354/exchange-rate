package dev.jihun.domain.exchangerate.dto;

import lombok.Getter;

@Getter
public class ReceivedAmountCalculationRequest {

    private String receiveCountry;
    private double remittanceAmount;

}
