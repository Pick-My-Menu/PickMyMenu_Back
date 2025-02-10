package com.pmm.pickmymenu_back.service;


import com.pmm.pickmymenu_back.domain.Choice;
import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.dto.GeminiRequest;
import com.pmm.pickmymenu_back.dto.GeminiResponse;
import com.pmm.pickmymenu_back.exception.MemberException;
import com.pmm.pickmymenu_back.repository.ChoiceRepository;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Random;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final ResultMenuRepository resultMenuRepository;
    private final MemberRepository memberRepository;
    private final ChoiceRepository choiceRepository;
    private final JWTUtil jwtUtil;
    private final Random random = new Random();

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


    // 오늘의 추천
    public List<String> getTodayPick(String token) {
        if (token == null || token.isEmpty()) {
            throw new MemberException("로그인이 필요합니다.");
        }

        List<Choice> choices = choiceRepository.findRandomChoices();

        // 랜덤으로 가져온 선택지에서 question 둘 중 하나 다시 랜덤으로 선택하기
        return choices.stream()
                .map(choice -> random.nextBoolean() ? choice.getQuestion0() : choice.getQuestion1())
                .toList();
    }


    public String processSelection(String selectedItem) {

        System.out.println("Received selection: " + selectedItem);

        return "선택한 항목 '" + selectedItem + "'을(를) 처리했습니다.";
    }


}