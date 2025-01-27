package com.pmm.pickmymenu_back.service;

import static org.junit.jupiter.api.Assertions.*;

import com.pmm.pickmymenu_back.dto.request.rank.RankRestaurantReq;
import com.pmm.pickmymenu_back.dto.response.rank.RankRestaurantRes;
import com.pmm.pickmymenu_back.dto.response.rank.RankSurveyRes;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("dev")
@Transactional
class RankServiceTest {

    @Autowired
    RankService rankService;

    @Test
    void getSurveyRank() {
        List<RankSurveyRes> surveyRank = rankService.getSurveyRank(null);
        System.out.println(surveyRank);

    }

    @Test
    void getRestaurantRank(){
        RankRestaurantReq req = new RankRestaurantReq();
        req.setType("DAY");
        req.setMenuName("닭갈비");
        //TODO 이거 출력하면 램번호나옴 확인필요
        List<Map<String, Object>> restaurantRank = rankService.getRestaurantRank(req);
        restaurantRank.forEach(res -> {
            System.out.println(res.get("resCount").toString());
            System.out.println(res.get("menu").toString());
            System.out.println(res.get("placeName").toString());
        });

    }
}