package dev.jihun.domain.exchangerate.domain;

import dev.jihun.domain.exchangerate.exception.InvalidRecipientCountryException;
import dev.jihun.domain.exchangerate.exception.InvalidRemittanceAmountException;
import java.text.DecimalFormat;
import java.util.Arrays;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExchangeRate {

    private double krw;
    private double jpy;
    private double php;

    @Builder
    public ExchangeRate(double krw, double jpy, double php) {
        this.krw = krw;
        this.jpy = jpy;
        this.php = php;
    }

    public String receiveAmountCalculation(double remittanceAmount, String recipientCountry) {
        remittanceAmountValidation(remittanceAmount);
        RecipientCountry country = RecipientCountry.of(recipientCountry);
        return amountFormat(calculation(remittanceAmount, country));
    }

    private double calculation(double remittanceAmount, RecipientCountry country) {
        double receiveAmount = 0.0;
        switch (country) {
            case KRW:
                receiveAmount = krw * remittanceAmount;
                break;
            case JPY:
                receiveAmount = jpy * remittanceAmount;
                break;
            case PHP:
                receiveAmount = php * remittanceAmount;
                break;
        }
        return receiveAmount;
    }

    private void remittanceAmountValidation(double remittanceAmount) {
        if (remittanceAmount < 0 || remittanceAmount > 10000) {
            throw new InvalidRemittanceAmountException("입력한 송금액은 : " + remittanceAmount + " 입니다.");
        }
    }

    private String amountFormat(double receiveAmount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(receiveAmount);
    }

    enum RecipientCountry {
        KRW,
        JPY,
        PHP;

        static RecipientCountry of(String recipientCountry) {
            return Arrays.stream(values())
                        .filter(value -> recipientCountry.equals(value.name()))
                        .findFirst()
                        .orElseThrow(() -> new InvalidRecipientCountryException(recipientCountry + "는 지원하지 않는 수취국가 입니다."));
        }
    }
}