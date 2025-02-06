package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.choice.ChoiceUpdateReq;
import com.pmm.pickmymenu_back.dto.request.choice.GetRandomReq;
import com.pmm.pickmymenu_back.dto.response.choice.GetRandomRes;
import com.pmm.pickmymenu_back.service.ChoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/choice")
@Slf4j
public class ChoiceController {

    private final ChoiceService choiceService;

    @PostMapping("/random")
    public BaseResponse<GetRandomRes> getRandom(@RequestBody GetRandomReq req) {
        log.info(req.toString());
        GetRandomRes response = choiceService.getRandom(req);
        return BaseResponse.success(response);
    }

    @PostMapping()
    public BaseResponse<String> adminCreate(@RequestBody ChoiceUpdateReq req,
            @CookieValue(value = "token", required = false) String token) {
        String result = choiceService.adminCreate(req, token);
        return BaseResponse.success(result);
    }

    @PutMapping("/{id}")
    public BaseResponse<String> adminUpdate(@PathVariable Long id, @RequestBody ChoiceUpdateReq req,
            @CookieValue(value = "token", required = false) String token) {
        String result = choiceService.adminUpdate(id, req, token);
        return BaseResponse.success(result);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<String> adminDelete(@PathVariable Long id,
            @CookieValue(value = "token", required = false) String token) {
        String result = choiceService.adminDelete(id, token);
        return BaseResponse.success(result);
    }
}
