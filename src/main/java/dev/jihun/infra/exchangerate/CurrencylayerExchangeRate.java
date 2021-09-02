package dev.jihun.infra.exchangerate;

import dev.jihun.infra.exchangerate.CurrencylayerExchangeRateResponse.Quotes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class CurrencylayerExchangeRate implements ExchangeRate {

    private final RestTemplate restTemplate;
    private static final String ACCESS_KEY = "74ff11c1d5b60497fcf44ee5f196a6a3";

    @Override
    public dev.jihun.domain.exchangerate.domain.ExchangeRate exchangeRateInquiry() {
        CurrencylayerExchangeRateResponse currencylayerExchangeRateResponse = restTemplate.getForObject("/api/live?access_key=" + ACCESS_KEY, CurrencylayerExchangeRateResponse.class);
        Quotes quotes = currencylayerExchangeRateResponse.getQuotes();

        return dev.jihun.domain.exchangerate.domain.ExchangeRate
                                                .builder()
                                                .krw(quotes.USDKRW)
                                                .jpy(quotes.USDJPY)
                                                .php(quotes.USDPHP)
                                                .build();
    }
}
