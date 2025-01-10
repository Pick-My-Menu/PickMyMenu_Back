package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.survey.SurveyCollectReq;
import com.pmm.pickmymenu_back.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/collect")
    public BaseResponse<Long> collect(@RequestBody SurveyCollectReq req) {
        System.out.println("================");
        System.out.println(req);
        System.out.println("================");

        Long result = surveyService.collect(req);
        return BaseResponse.success(result);
    }
    @GetMapping("/rank")
    public List<Object[]> getRank() {
        return surveyService.getRank();
    }

}
