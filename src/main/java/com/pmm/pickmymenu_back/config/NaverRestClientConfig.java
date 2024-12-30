package com.pmm.pickmymenu_back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class NaverRestClientConfig {

    @Value("${data.naver.id}")
    private String NAVER_API_ID;
    @Value("${data.naver.secret}")
    private String NAVER_API_SECRET;
    @Bean
    public RestClient naverRestClient(){
        return RestClient.builder()
                .baseUrl("https://openapi.naver.com/v1")
                .defaultHeaders(headers -> {
                    headers.set("X-Naver-Client-Id", NAVER_API_ID);
                    headers.set("X-Naver-Client-Secret", NAVER_API_SECRET);
                }).build();
    }
}