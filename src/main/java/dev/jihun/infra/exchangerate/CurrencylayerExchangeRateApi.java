package dev.jihun.infra.exchangerate;

import dev.jihun.domain.exchangerate.domain.ExchangeRate;
import dev.jihun.infra.exchangerate.CurrencylayerExchangeRateResponse.Quotes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class CurrencylayerExchangeRateApi implements ExchangeRateApi {

    @Qualifier("currencylayer")
    private final RestTemplate restTemplate;
    private static final String ACCESS_KEY = "74ff11c1d5b60497fcf44ee5f196a6a3";

    @Cacheable("exchangeRate")
    @Override
    public ExchangeRate exchangeRateInquiry() {
        CurrencylayerExchangeRateResponse currencylayerExchangeRateResponse = restTemplate.getForObject("/api/live?access_key=" + ACCESS_KEY, CurrencylayerExchangeRateResponse.class);
        Quotes quotes = currencylayerExchangeRateResponse.getQuotes();

        return ExchangeRate.builder()
            .krw(quotes.USDKRW)
            .jpy(quotes.USDJPY)
            .php(quotes.USDPHP)
            .build();
    }
}
