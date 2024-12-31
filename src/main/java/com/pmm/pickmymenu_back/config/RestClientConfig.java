package com.pmm.pickmymenu_back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestClientConfig {

    @Value("${data.naver.id}")
    private String NAVER_API_ID;
    @Value("${data.naver.secret}")
    private String NAVER_API_SECRET;
    @Value("${data.kakao.key}")
    private String KAKAO_API_KEY;
    @Bean
    public RestClient naverRestClient(){
        return RestClient.builder()
                .baseUrl("https://openapi.naver.com/v1")
                .defaultHeaders(headers -> {
                    headers.set("X-Naver-Client-Id", NAVER_API_ID);
                    headers.set("X-Naver-Client-Secret", NAVER_API_SECRET);
                }).build();
    }


    @Bean
    public WebClient kakaoRestClient(){
        return WebClient.builder()
                .baseUrl("https://dapi.kakao.com/v2")
                .defaultHeaders(headers -> {
                    headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);
                    headers.set("content-type", "application/json;charset=UTF-8");
                })
                .build();
    }
}
