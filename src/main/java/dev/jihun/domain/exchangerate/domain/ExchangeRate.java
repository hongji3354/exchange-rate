package dev.jihun.domain.exchangerate.domain;

import dev.jihun.domain.exchangerate.exception.InvalidRecipientCountryException;
import dev.jihun.domain.exchangerate.exception.InvalidRemittanceAmountException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

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

    public double calculationBasedExchangeRate(String recipientCountry) {
        RecipientCountry country = RecipientCountry.of(recipientCountry);
        switch (country) {
            case KRW:
                return krw;
            case JPY:
                return jpy;
            case PHP:
                return php;
        }
        return 0.0;
    }

    public double receiveAmountCalculation(int remittanceAmount, String recipientCountry) {
        remittanceAmountValidation(remittanceAmount);
        double exchangeRate = calculationBasedExchangeRate(recipientCountry);
        return calculation(remittanceAmount, exchangeRate);
    }

    private double calculation(int remittanceAmount, double exchangeRate) {
        return exchangeRate * remittanceAmount;
    }

    private void remittanceAmountValidation(int remittanceAmount) {
        if (remittanceAmount <= 0 || remittanceAmount > 10000) {
            throw new InvalidRemittanceAmountException("입력한 송금액은 : " + remittanceAmount + " 입니다.");
        }
    }

    enum RecipientCountry {
        KRW,
        JPY,
        PHP;

        static RecipientCountry of(String recipientCountry) {
            return Arrays.stream(values())
                        .filter(value -> recipientCountry.equalsIgnoreCase(value.name()))
                        .findFirst()
                        .orElseThrow(() -> new InvalidRecipientCountryException(recipientCountry + "는 지원하지 않는 수취국가 입니다."));
        }
    }
}