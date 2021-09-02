package dev.jihun.global.config.resttemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

@Slf4j
public class RestTemplateClientInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
        ClientHttpRequestExecution execution) throws IOException {
        requestLogging(request, body);

        final ClientHttpResponse response = execution.execute(request, body);

        responseLogging(response);

        return response;
    }

    private void requestLogging(final HttpRequest request, byte[] body) {
        log.info("===== Request Start ======");
        log.info("Headers: {}", request.getHeaders());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request URI: {}", request.getURI());
        log.info("Request body: {}", body.length == 0 ? null : new String(body, StandardCharsets.UTF_8));
        log.info("===== Request End ======");
    }

    private void responseLogging(ClientHttpResponse response) throws IOException {
        final String body = getBody(response);

        log.info("====== Response Start =======");
        log.info("Headers: {}", response.getHeaders());
        log.info("Response Status : {}", response.getRawStatusCode());
        log.info("Response body: {}", body);
        log.info("======Response=======");

    }

    private String getBody(final ClientHttpResponse response) throws IOException {
        return StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
    }
}
