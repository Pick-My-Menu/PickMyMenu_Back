package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.Choice;
import com.pmm.pickmymenu_back.dto.request.choice.GetRandomReq;
import com.pmm.pickmymenu_back.dto.response.choice.GetRandomRes;
import com.pmm.pickmymenu_back.repository.ChoiceRepository;

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
}
