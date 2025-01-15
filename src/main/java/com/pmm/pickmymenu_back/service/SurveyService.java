package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.Choice;
import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.ResultMenu;
import com.pmm.pickmymenu_back.domain.Survey;
import com.pmm.pickmymenu_back.domain.SurveyGroup;
import com.pmm.pickmymenu_back.dto.request.survey.SurveyCollectReq;
import com.pmm.pickmymenu_back.repository.ChoiceRepository;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import com.pmm.pickmymenu_back.repository.SurveyGroupRepository;
import com.pmm.pickmymenu_back.repository.SurveyRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SurveyService {

    private final ChoiceRepository choiceRepository;
    private final ResultMenuRepository resultMenuRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyGroupRepository surveyGroupRepository;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;


    public Long collect(SurveyCollectReq req, String token) {
        Member member = null;
        if (token != null) {
            String email = jwtUtil.validateAndExtract(token);
            Optional<Member> existMember = memberRepository.findByEmail(email);
            if (existMember.isPresent()) {
                member = existMember.get();
            }
        }

        ResultMenu resultMenu = ResultMenu.create(member, req.getMenu());
        resultMenuRepository.save(resultMenu);
        // 방금 저장된 resultMenu id값 반환

        SurveyGroup surveyGroup = SurveyGroup.create(member, resultMenu);
        surveyGroupRepository.save(surveyGroup);

        req.getList().forEach(surveyCollect -> {
            Choice choice = choiceRepository.findById(surveyCollect.getId())
                    .orElseThrow(() -> new RuntimeException("초이스 아이디가 없습니다."));
            Survey survey = Survey.create(surveyGroup, choice, surveyCollect);
            surveyRepository.save(survey);
        });

        return resultMenu.getId();
    }

    public List<Object[]> getRank() {
        return null;
    }

}
