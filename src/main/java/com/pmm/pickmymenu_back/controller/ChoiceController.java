package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.choice.GetRandomReq;
import com.pmm.pickmymenu_back.dto.response.choice.GetRandomRes;
import com.pmm.pickmymenu_back.service.ChoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

}
