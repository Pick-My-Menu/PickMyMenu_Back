package com.pmm.pickmymenu_back.controller;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.rank.RankMenuReq;
import com.pmm.pickmymenu_back.dto.request.rank.RankRestaurantReq;
import com.pmm.pickmymenu_back.dto.request.rank.RankSurveyReq;
import com.pmm.pickmymenu_back.dto.response.rank.RankMenuRes;
import com.pmm.pickmymenu_back.dto.response.rank.RankRestaurantRes;
import com.pmm.pickmymenu_back.dto.response.rank.RankSurveyRes;
import com.pmm.pickmymenu_back.service.RankService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rank")
@Slf4j
public class RankController {

    private final RankService rankService;

    @GetMapping("/menu")
    public BaseResponse<List<RankMenuRes>> getRankMenu(@ModelAttribute RankMenuReq req) {
        return BaseResponse.success(rankService.getRankMenu(req));
    }

    @GetMapping("/survey")
    public BaseResponse<List<RankSurveyRes>> getSurveyRank(@ModelAttribute RankSurveyReq req) {
        return BaseResponse.success(rankService.getSurveyRank(req));
    }

    @GetMapping("/restaurant")
    public BaseResponse<List<Map<String, Object>>> getRestaurantRank(
            @ModelAttribute RankRestaurantReq req) {
        return BaseResponse.success(rankService.getRestaurantRank(req));
    }
}
