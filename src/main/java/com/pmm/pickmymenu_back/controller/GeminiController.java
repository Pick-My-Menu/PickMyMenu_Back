package com.pmm.pickmymenu_back.controller;


import com.pmm.pickmymenu_back.dto.SelectionRequest;
import com.pmm.pickmymenu_back.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @GetMapping("/gemini/question")
    public ResponseEntity<?> gemini(@RequestParam String prompt) {
        System.out.println(prompt);
        try {
            return ResponseEntity.ok().body(geminiService.getContents(prompt + " 이것들이 포함된 식당에서 먹을 수 있는 음식 3가지 정도만 추천해줘. 대답은 한국말로."));
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