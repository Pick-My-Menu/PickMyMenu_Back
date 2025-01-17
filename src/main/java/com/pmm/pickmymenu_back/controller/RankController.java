package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.rank.RankMenuReq;
import com.pmm.pickmymenu_back.dto.response.rank.RankMenuRes;
import com.pmm.pickmymenu_back.service.RankService;
import java.util.List;
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

}
