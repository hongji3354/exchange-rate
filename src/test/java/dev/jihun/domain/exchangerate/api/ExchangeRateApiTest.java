package dev.jihun.domain.exchangerate.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jihun.domain.exchangerate.application.ExchangeRateApplication;
import dev.jihun.domain.exchangerate.dto.ExchangeRateInformationResponse;
import dev.jihun.domain.exchangerate.dto.ReceivedAmountCalculationRequest;
import dev.jihun.domain.exchangerate.dto.ReceivedAmountCalculationResponse;
import dev.jihun.global.util.NumberFormatUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = ExchangeRateApi.class)
class ExchangeRateApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateApplication exchangeRateApplication;

    @Mock
    private dev.jihun.infra.exchangerate.ExchangeRateApi exchangeRateApi;

    @Autowired
    private ObjectMapper objectMapper;

    private final double usaToKrw = 1159.00;
    private final double usaToJpy = 110.00;
    private final double usaToPhp = 49.85;

    @Test
    @DisplayName("환율 정보 조회")
    void exchange_rate_information_test() throws Exception {
        //given
        when(exchangeRateApplication.getExchangeRateInformation()).thenReturn(
            ExchangeRateInformationResponse.builder().krw(usaToKrw).jpy(usaToJpy).php(usaToPhp)
                .build());

        //when
        ResultActions response = mockMvc.perform(get("/exchange-rate"))
            .andDo(print());

        //then
        response.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.krw", is(usaToKrw)))
            .andExpect(jsonPath("$.jpy", is(usaToJpy)))
            .andExpect(jsonPath("$.php", is(usaToPhp)));


    }

    @Test
    @DisplayName("수취 금액 계산")
    void received_amount_calculation_test() throws Exception {
        //given
        ReceivedAmountCalculationRequest receivedAmountCalculationRequest = objectMapper.readValue("{ \"receiveCountry\" : \"KRW\", \"remittanceAmount\" :  1000}", ReceivedAmountCalculationRequest.class);
        String result = NumberFormatUtil.format(receivedAmountCalculationRequest.getRemittanceAmount() * usaToKrw);
        when(exchangeRateApplication.receivedAmountCalculation(any(ReceivedAmountCalculationRequest.class))).thenReturn(new ReceivedAmountCalculationResponse(result));

        //when
        ResultActions response = mockMvc.perform(post("/receive-amount/calculation")
                .content(objectMapper.writeValueAsString(receivedAmountCalculationRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        //then
        response.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.receivedAmount", is(result)));
    }

    @Test
    @DisplayName("수취 국가 누락")
    void missing_receipt_country_test() throws Exception {
        //given
        ReceivedAmountCalculationRequest receivedAmountCalculationRequest = objectMapper.readValue("{\"remittanceAmount\" :  1000}", ReceivedAmountCalculationRequest.class);

        //when
        ResultActions response = mockMvc.perform(post("/receive-amount/calculation")
                .content(objectMapper.writeValueAsString(receivedAmountCalculationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        response.andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("송금 금액 0미만 일시")
    void remittance_amount_less_than_zero_dollar_test() throws Exception {
        //given
        ReceivedAmountCalculationRequest receivedAmountCalculationRequest = objectMapper.readValue("{ \"receiveCountry\" : \"KRW\", \"remittanceAmount\" :  0}", ReceivedAmountCalculationRequest.class);

        //when
        ResultActions response = mockMvc.perform(post("/receive-amount/calculation")
                .content(objectMapper.writeValueAsString(receivedAmountCalculationRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        //then
        response.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("송금 금액 10000초과 일시")
    void remittance_amount_more_than_ten_thousand_dollar_test() throws Exception {
        //given
        ReceivedAmountCalculationRequest receivedAmountCalculationRequest = objectMapper.readValue("{ \"receiveCountry\" : \"KRW\", \"remittanceAmount\" :  10001}", ReceivedAmountCalculationRequest.class);

        //when
        ResultActions response = mockMvc.perform(post("/receive-amount/calculation")
                .content(objectMapper.writeValueAsString(receivedAmountCalculationRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        //then
        response.andExpect(status().isBadRequest());
    }

}