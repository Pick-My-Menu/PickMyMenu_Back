package com.pmm.pickmymenu_back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PublicDataController {

    // API_KEY는 application.properties에 저장된 값을 읽어옵니다.
    @Value("${public.api.key}")
    private String apiKey; // API_KEY 값은 application.properties 파일에서 설정합니다.

    private final RestTemplate restTemplate;

    // RestTemplate 주입
    public PublicDataController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/public-data")
    public String getPublicData(
            @RequestParam(defaultValue = "1") int startIndex,
            @RequestParam(defaultValue = "5") int endIndex,
            @RequestParam(defaultValue = "쌀") String irdntNm,
            @RequestParam(defaultValue = "xml") String type) {

        // API URL을 동적으로 구성
        String url = String.format("http://211.237.50.150:7080/openapi/sample/%s/%d/%d?API_KEY=%s&TYPE=%s&IRDNT_NM=%s",
                "Grid_20150827000000000227_1", startIndex, endIndex, apiKey, type, irdntNm);

        // API 호출
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // API 응답 반환
        return response.getBody(); // XML 또는 JSON 형식의 응답 본문 반환
    }
}