package com.pmm.pickmymenu_back.service;


import com.pmm.pickmymenu_back.dto.GeminiRequest;
import com.pmm.pickmymenu_back.dto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Qualifier("geminiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String getContents(String prompt) {

        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        GeminiRequest request = new GeminiRequest(prompt);
        GeminiResponse response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class);

        String message = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();
        System.out.println(message);

        return message;
    }


    public String processSelection(String selectedItem) {
        // 선택된 항목에 따라 처리 로직을 작성
        // 예: 데이터베이스에 저장, 비즈니스 로직 수행 등
        System.out.println("Received selection: " + selectedItem);

        // 샘플 응답
        return "선택한 항목 '" + selectedItem + "'을(를) 처리했습니다.";
    }




}