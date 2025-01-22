package com.pmm.pickmymenu_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class SerachService {

    @Value("${data.naver.id}")
    private String NAVER_API_ID;
    @Value("${data.naver.secret}")
    private String NAVER_API_SECRET;

    private final RestTemplate restTemplate;

    public String getContents(String text) {
        // 검색어를 UTF-8로 인코딩
        try {
            String encodedText = java.net.URLEncoder.encode(text, "UTF-8");
            String apiUrl = "https://openapi.naver.com/v1/search/blog?query=" + encodedText;

            // HTTP 요청 헤더 설정
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("X-Naver-Client-Id", NAVER_API_ID);
            headers.set("X-Naver-Client-Secret", NAVER_API_SECRET);

            // HTTP 요청을 보내고 응답을 그대로 받아옴
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
            org.springframework.http.ResponseEntity<String> response = restTemplate.exchange(apiUrl, org.springframework.http.HttpMethod.GET, entity, String.class);

            System.out.println(response);

            return response.getBody(); // JSON 형태로 응답 반환

        } catch (Exception e) {
            throw new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "네이버 API 호출 실패: " + e.getMessage());
        }
    }


}
