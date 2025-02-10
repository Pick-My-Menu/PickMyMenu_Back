package com.pmm.pickmymenu_back.controller;


import com.pmm.pickmymenu_back.dto.SelectionRequest;
import com.pmm.pickmymenu_back.exception.MemberException;
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
        System.out.println(select);
        try {
            return ResponseEntity.ok().body(geminiService.getContents(select +
                    "Please recommend three foods that match these three keywords.\n" +
                    "They must be foods that can be eaten at restaurants in Korea.\n" +
                    "Exclude adjectives and provide only the menu names.\n" +
                    "Please answer in Korean. \n" +
                    "\n" +
                    "Answer format:\n" +
                    "1. First food name\n" +
                    "2. Second food name\n" +
                    "3. Third food name"
//                    "I recommend food that corresponds to this keyword\n"
//                    + "You know all the restaurants that exist in Korea\n"
//                    + "I know all the knowledge associated with the characteristics of the material\n"
//                    + "When I recommend food, I recommend three kinds of food\n"
//                    + "I don't need an explanation of the food. Just tell me the name of the food\n"
//                    + "Please answer in Korean"
            ));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 오늘의 추천
    @GetMapping("/todayPick")
    public ResponseEntity<?> todayPick(@CookieValue(value = "token", required = false) String token) {
        try {
            if (token == null || token.isEmpty()) {
                return ResponseEntity.badRequest().body("로그인이 필요합니다.");
            }

            List<String> keywords = geminiService.getTodayPick(token);
            System.out.println(keywords);

            String prompt = String.join(", ", keywords) + "\n" +
                    "Please recommend three foods that match these three keywords.\n" +
                    "They must be foods that can be eaten at restaurants in Korea.\n" +
                    "Exclude adjectives and provide only the menu names.\n" +
                    "Please answer in Korean. \n" +
                    "\n" +
                    "Answer format:\n" +
                    "1. First food name\n" +
                    "2. Second food name\n" +
                    "3. Third food name";

            return ResponseEntity.ok().body(Collections.singletonList(geminiService.getContents(prompt)));
        } catch (MemberException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Collections.singletonList(e.getMessage()));
        }
    }


    // 선택된 값을 처리하는 엔드포인트
    @PostMapping("/gemini/selection")
    public ResponseEntity<String> handleSelection(@RequestBody SelectionRequest request) {
        String result = geminiService.processSelection(request.getSelected());
        return ResponseEntity.ok(result);
    }


}