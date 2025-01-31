package com.pmm.pickmymenu_back.service;


import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.dto.GeminiRequest;
import com.pmm.pickmymenu_back.dto.GeminiResponse;
import com.pmm.pickmymenu_back.exception.MemberException;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final ResultMenuRepository resultMenuRepository;
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

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
        if (token == null) throw new MemberException("토큰이 존재하지 않습니다.");
        String email = jwtUtil.validateAndExtract(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("해당 사용자를 찾을 수 없습니다."));

        return resultMenuRepository.findMenuByMember(member); // 모두 리턴

//        List<String> menus = resultMenuRepository.findMenuByMember(member);
//
//        // 5개만 리턴
//        return menus.size() > 5 ? menus.subList(0, 5) : menus; // 5개만 리턴
    }



    public String processSelection(String selectedItem) {
        // 선택된 항목에 따라 처리 로직을 작성
        // 예: 데이터베이스에 저장, 비즈니스 로직 수행 등
        System.out.println("Received selection: " + selectedItem);

        // 샘플 응답
        return "선택한 항목 '" + selectedItem + "'을(를) 처리했습니다.";
    }


}