package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.Choice;
import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.dto.request.choice.ChoiceUpdateReq;
import com.pmm.pickmymenu_back.dto.request.choice.GetRandomReq;
import com.pmm.pickmymenu_back.dto.response.choice.GetRandomRes;
import com.pmm.pickmymenu_back.exception.MemberException;
import com.pmm.pickmymenu_back.repository.ChoiceRepository;

import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChoiceService {

    private final ChoiceRepository choiceRepository;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public GetRandomRes getRandom(GetRandomReq req) {
        Choice choice;
        if (req.getChoiceIdList() == null) {
            choice = choiceRepository.getRandom()
                    .orElseThrow(() -> new RuntimeException("쿼리에 문제가 있습니다."));
        }else {
            choice = choiceRepository.getRandom(req.getChoiceIdList())
                    .orElseThrow(() -> new RuntimeException("쿼리에 문제가 있습니다."));
            choice.countPlus();
        }
        choiceRepository.save(choice);
        return new GetRandomRes(choice);
    }

    public String adminUpdate(Long id, ChoiceUpdateReq req, String token) {
        memberService.adminCheck(token);

        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Choice ID 가 존재하지 않습니다."));
        choice.update(req);
        choiceRepository.save(choice);
        return "업데이트 성공";
    }

    public String adminCreate(ChoiceUpdateReq req, String token) {
        memberService.adminCheck(token);
        Choice choice = Choice.create(req.getQuestion0(), req.getQuestion1());
        choiceRepository.save(choice);
        return "success";
    }

    public String adminDelete(Long id, String token) {
        memberService.adminCheck(token);
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Choice ID 가 존재하지 않습니다."));
        choiceRepository.delete(choice);
        return "success";
    }
}
