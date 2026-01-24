package org.example.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.mock.MockAssetResponse; // DTO import 필수
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyDataMockAdapter {

    private final RestTemplate restTemplate;

    @Value("${mydata.mock.base-url}") // application.properties 확인
    private String mockBaseUrl;

    // 공통 헤더
    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    // =================================================================
    // 1. Bank (은행) - GET 호출
    // =================================================================
    public MockAssetResponse<MockAssetResponse.BankAccount> getBankAccounts(String token) {
        String url = mockBaseUrl + "/bank/accounts"; // 명세서 주소

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                new ParameterizedTypeReference<MockAssetResponse<MockAssetResponse.BankAccount>>() {}
        ).getBody();
    }

    // =================================================================
    // 2. Card (카드) - GET 호출
    // =================================================================
    public MockAssetResponse<MockAssetResponse.Card> getCards(String token) {
        String url = mockBaseUrl + "/card/cards";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                new ParameterizedTypeReference<MockAssetResponse<MockAssetResponse.Card>>() {}
        ).getBody();
    }

    // =================================================================
    // 3. Invest (증권) - GET 호출
    // =================================================================
    public MockAssetResponse<MockAssetResponse.SecurityAccount> getInvestAccounts(String token) {
        String url = mockBaseUrl + "/invest/accounts";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                new ParameterizedTypeReference<MockAssetResponse<MockAssetResponse.SecurityAccount>>() {}
        ).getBody();
    }

    // =================================================================
    // 4. Insurance (보험) - GET 호출
    // =================================================================
    public MockAssetResponse<MockAssetResponse.Insurance> getInsuContracts(String token) {
        String url = mockBaseUrl + "/insu/contracts";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(token)),
                new ParameterizedTypeReference<MockAssetResponse<MockAssetResponse.Insurance>>() {}
        ).getBody();
    }
}