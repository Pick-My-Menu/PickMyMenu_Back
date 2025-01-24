package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.request.rank.RankMenuReq;
import com.pmm.pickmymenu_back.dto.request.rank.RankRestaurantReq;
import com.pmm.pickmymenu_back.dto.request.rank.RankSurveyReq;
import com.pmm.pickmymenu_back.dto.response.rank.RankMenuRes;
import com.pmm.pickmymenu_back.dto.response.rank.RankRestaurantRes;
import com.pmm.pickmymenu_back.dto.response.rank.RankSurveyRes;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import com.pmm.pickmymenu_back.repository.SurveyRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RankService {

    private final ResultMenuRepository resultMenuRepository;
    private final SurveyRepository surveyRepository;

    @Transactional(readOnly = true)
    public List<RankMenuRes> getRankMenu(RankMenuReq req) {
        return resultMenuRepository.findMenuCountsOrderByDesc(req.getTime());
    }

    @Transactional(readOnly = true)
    public List<RankSurveyRes> getSurveyRank(RankSurveyReq req) {
        return surveyRepository.findSurveyByChoiceId();
    }

    @Transactional(readOnly = true)
    public List<RankRestaurantRes> getRestaurantRank(RankRestaurantReq req) {
        return resultMenuRepository.findMenuByRestaurant(req.getMenuName(), req.getTime());
    }
}
