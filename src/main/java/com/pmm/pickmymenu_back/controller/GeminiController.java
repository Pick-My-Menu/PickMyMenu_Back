package com.pmm.pickmymenu_back.controller;


import com.pmm.pickmymenu_back.dto.SelectionRequest;
import com.pmm.pickmymenu_back.service.GeminiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @GetMapping("/gemini/question")
    public ResponseEntity<?> gemini(@RequestParam String select) {

        System.out.println(select);
        try {
            return ResponseEntity.ok().body(geminiService.getContents(select +
                    " 이 키워드와 아래 조건을 가지고 음식 3가지만 추천해줘. 대답은 한국말로." +
                    "1. 음식의 설명은 뺴고 음식의 이름말 알려준다" +
                    "2. 식당에서 파는 음식명 이어야한다. " +
                    "3. 주어진 두가지의 키워드와 최대한 부합하는 메뉴만 추천한다."
            ));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 선택된 값을 처리하는 엔드포인트
    @PostMapping("/gemini/selection")
    public ResponseEntity<String> handleSelection(@RequestBody SelectionRequest request) {
        String result = geminiService.processSelection(request.getSelected());
        return ResponseEntity.ok(result);
    }



}