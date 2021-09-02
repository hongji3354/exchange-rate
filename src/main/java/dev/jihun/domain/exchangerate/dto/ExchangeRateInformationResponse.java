package dev.jihun.domain.exchangerate.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExchangeRateInformationResponse {

    private double krw;
    private double jpy;
    private double php;

}
