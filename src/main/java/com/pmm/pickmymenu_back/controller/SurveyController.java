package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.survey.SurveyCollectReq;
import com.pmm.pickmymenu_back.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/collect")
    public BaseResponse<Object> collect(@RequestBody SurveyCollectReq req) {
        System.out.println(req);

        boolean result = surveyService.collect(req);
        return BaseResponse.success(result);
    }

    @GetMapping("/parentCount")
    public List<Object[]> getParentFoodTreeCategoryCount() {
        return surveyService.getParentFoodTreeCategoryCount();
    }


}
