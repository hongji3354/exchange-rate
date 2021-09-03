package dev.jihun.domain.exchangerate.application;

import dev.jihun.domain.exchangerate.domain.ExchangeRate;
import dev.jihun.domain.exchangerate.exception.InvalidRecipientCountryException;
import dev.jihun.domain.exchangerate.exception.InvalidRemittanceAmountException;
import dev.jihun.infra.exchangerate.CurrencylayerExchangeRateApi;
import dev.jihun.infra.exchangerate.ExchangeRateApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

class ExchangeRateApplicationTest {

    private ExchangeRateApi exchangeRateApi;
    private final double usaToKrw = 1159.00;
    private final double usaToJpy = 110.00;
    private final double usaToPhp = 49.85;

    @BeforeEach
    void init() {
        exchangeRateApi = Mockito.mock(CurrencylayerExchangeRateApi.class);
        when(exchangeRateApi.exchangeRateInquiry()).thenReturn(ExchangeRate.builder().krw(usaToKrw).jpy(usaToJpy).php(usaToPhp).build());
    }

    @Test
    @DisplayName("수취 금액 계산")
    void received_amount_calculation_test() {
        //given
        final int remittanceAmount = 100;
        final ExchangeRate exchangeRate = exchangeRateApi.exchangeRateInquiry();
        final String recipientCountry = "KRW";

        //when
        double receiveAmountCalculation = exchangeRate.receiveAmountCalculation(remittanceAmount, recipientCountry);

        //then
        assertThat(receiveAmountCalculation, is(usaToKrw * remittanceAmount));
    }

    @Test
    @DisplayName("송금액 0 달러 미만 예외")
    void remittance_amount_less_than_zero_dollar_test() {
        //given
        final int remittanceAmount = 0;
        final ExchangeRate exchangeRate = exchangeRateApi.exchangeRateInquiry();
        final String recipientCountry = "KRW";

        //when and then
        assertThatThrownBy(() -> exchangeRate.receiveAmountCalculation(remittanceAmount, recipientCountry))
                .isInstanceOf(InvalidRemittanceAmountException.class)
                .hasMessage("입력한 송금액은 : " + remittanceAmount + " 입니다.");
    }

    @Test
    @DisplayName("송금액 10000원 초과 예외")
    void remittance_amount_more_than_ten_thousand_dollar_test() {
        //given
        final int remittanceAmount = 10001;
        final ExchangeRate exchangeRate = exchangeRateApi.exchangeRateInquiry();
        final String recipientCountry = "KRW";

        //when and then
        assertThatThrownBy(() -> exchangeRate.receiveAmountCalculation(remittanceAmount, recipientCountry))
                .isInstanceOf(InvalidRemittanceAmountException.class)
                .hasMessage("입력한 송금액은 : " + remittanceAmount + " 입니다.");
    }

    @Test
    @DisplayName("환전을 지원하지 않는 나라 예외")
    void unsupportedCountryTest() {
        //given
        final int remittanceAmount = 10000;
        final ExchangeRate exchangeRate = exchangeRateApi.exchangeRateInquiry();
        final String recipientCountry = "USD";

        //when and then
        assertThatThrownBy(() -> exchangeRate.receiveAmountCalculation(remittanceAmount, recipientCountry))
                .isInstanceOf(InvalidRecipientCountryException.class)
                .hasMessage(recipientCountry + "는 지원하지 않는 수취국가 입니다.");
    }

}