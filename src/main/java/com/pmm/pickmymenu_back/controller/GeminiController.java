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


    // 선택된 값을 처리하는 엔드포인트
    @PostMapping("/gemini/selection")
    public ResponseEntity<String> handleSelection(@RequestBody SelectionRequest request) {
        String result = geminiService.processSelection(request.getSelected());
        return ResponseEntity.ok(result);
    }



}