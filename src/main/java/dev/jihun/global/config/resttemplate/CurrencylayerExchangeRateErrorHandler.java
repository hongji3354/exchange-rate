package dev.jihun.global.config.resttemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

@Slf4j
public class CurrencylayerExchangeRateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        String body = getBody(response);
        HttpStatus statusCode = response.getStatusCode();
        if (body.contains("false")) {
            return true;
        }
        return !statusCode.is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        //TODO : 캐싱 데이터가 없다면 Exception 처리하기.
        String body = getBody(response);

        log.error("================");
        log.error("Response Headers: {}", response.getHeaders());
        log.error("Response Status : {}", response.getRawStatusCode());
        log.error("Response body: {}", body);
        log.error("================");
    }

    private String getBody(final ClientHttpResponse response) throws IOException {
        return StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
    }
}
