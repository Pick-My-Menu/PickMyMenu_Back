package com.pmm.pickmymenu_back.controller;


import com.pmm.pickmymenu_back.dto.SelectionRequest;
import com.pmm.pickmymenu_back.service.GeminiService;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @GetMapping("/question")
    public ResponseEntity<?> gemini(@RequestParam String select) {
        try {
            return ResponseEntity.ok().body(geminiService.getContents(select +
                    "I recommend food that corresponds to this keyword\n"
                    + "You know all the restaurants that exist in Korea\n"
                    + "I know all the knowledge associated with the characteristics of the material\n"
                    + "When I recommend food, I recommend three kinds of food\n"
                    + "I don't need an explanation of the food. Just tell me the name of the food\n"
                    + "Please answer in Korean"
            ));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 오늘의 추천
    @GetMapping("/todayPick")
    public ResponseEntity<List<String>> todayPick(@CookieValue("token") String token) {
        List<String> menus = geminiService.getTodayPick(token);
        System.out.println(menus);
        try {
            return ResponseEntity.ok().body(Collections.singletonList(geminiService.getContents(menus +
                    "이 메뉴들 중에서 무작위로 3개만 골라줘.\n"
                    + "만약 제시된 메뉴가 3개 미만이면\n"
                    + "You know all the restaurants that exist in Korea\n"
                    + "I know all the knowledge associated with the characteristics of the material\n"
                    + "When I recommend food, I recommend three kinds of food\n"
                    + "I don't need an explanation of the food. Just tell me the name of the food\n"
                    + "Please answer in Korean\n"
                    + "이 조건으로 3개 골라줘\n"
                    + "1. ** 첫 번째 메뉴명 ** \n"
                    + "2. ** 두 번째 메뉴명 ** \n"
                    + "3. ** 세 번째 메뉴명 ** 이런 형태로"
            )));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
        }

    }


    // 선택된 값을 처리하는 엔드포인트
    @PostMapping("/gemini/selection")
    public ResponseEntity<String> handleSelection(@RequestBody SelectionRequest request) {
        String result = geminiService.processSelection(request.getSelected());
        return ResponseEntity.ok(result);
    }


}