package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.service.SerachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class SearchController {

    private final SerachService serachService;

    @GetMapping("/search")
    public ResponseEntity<?> blog(@RequestParam String text) {
        try {
            // 검색어를 받아서 서비스에서 API 호출 후 결과를 리턴
            return ResponseEntity.ok().body(serachService.getContents(text));
        } catch (HttpClientErrorException e) {
            // API 호출 중 에러가 발생하면, BAD_REQUEST 상태 코드로 메시지 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
