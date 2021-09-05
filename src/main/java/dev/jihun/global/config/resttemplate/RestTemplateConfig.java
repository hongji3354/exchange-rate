package dev.jihun.global.config.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final RestTemplateBuilder restTemplateBuilder;

    @Bean("currencylayer")
    public RestTemplate currencylayerExchangeRateTemplate() {
        return restTemplateBuilder.rootUri("http://apilayer.net")
            .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
            .interceptors(new RestTemplateClientInterceptor())
            .errorHandler(new CurrencylayerExchangeRateErrorHandler())
            .build();


    }
}
