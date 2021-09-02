package dev.jihun.domain.exchangerate.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReceivedAmountCalculationRequest {

    @NotBlank(message = "수취국가가 누락되었습니다.")
    private String receiveCountry;

    @Min(value = 1, message = "송금액은 1 USD 이상이여야 합니다.")
    @Max(value = 10000, message = "송금액은 10000 미만이여야 합니다.")
    private double remittanceAmount;

}
