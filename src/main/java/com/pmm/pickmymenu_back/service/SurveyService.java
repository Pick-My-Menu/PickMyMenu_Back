package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.FoodTree;
import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.ResultMenu;
import com.pmm.pickmymenu_back.domain.Survey;
import com.pmm.pickmymenu_back.dto.request.FoodTreeReq;
import com.pmm.pickmymenu_back.dto.request.survey.SurveyCollectReq;
import com.pmm.pickmymenu_back.repository.FoodTreeRepository;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import com.pmm.pickmymenu_back.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final ResultMenuRepository resultMenuRepository;
    private final SurveyRepository surveyRepository;
    private final FoodTreeRepository foodTreeRepository;


    public boolean collect(SurveyCollectReq req) {

        Member member = null;

        FoodTreeReq parentCategory = req.getParentCategory();
        FoodTree parentFoodTree = foodTreeRepository.findById(parentCategory.getId()).orElseThrow(() ->
                new IllegalArgumentException("FoodTree 의 아이디가 존재하지 않습니다.")
        );
        FoodTreeReq childCategory = req.getChildCategory();
        FoodTree childFoodTree = foodTreeRepository.findById(childCategory.getId()).orElseThrow(() ->
                new IllegalArgumentException("FoodTree 의 아이디가 존재하지 않습니다.")
        );

        ResultMenu resultMenu = ResultMenu.create(member, req.getSelectedKeyword());
        resultMenuRepository.save(resultMenu);
        Survey survey = Survey.create(member, resultMenu, parentFoodTree, childFoodTree);
        surveyRepository.save(survey);

        return true;
    }
}
