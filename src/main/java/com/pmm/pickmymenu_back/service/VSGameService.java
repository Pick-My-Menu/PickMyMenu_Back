package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.VSGame;
import com.pmm.pickmymenu_back.repository.VSGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class VSGameService {

    private final VSGameRepository vsGameRepository;

    public VSGame getRandomVSGroup1() {
        VSGame vsGame = vsGameRepository.findRandomByGroupId(1); // 랜덤한 vsGame 객체 반환
        if (vsGame != null) {
            String vs1 = vsGame.getVs1(); // vs1 값 가져오기
            String vs2 = vsGame.getVs2(); // vs2 값 가져오기
        }
        return vsGame;
    }


    public VSGame getRelatedVSGroup2(String selected){
        Random random = new Random();
        return vsGameRepository.findRandomByGroupId(2);
    }


}
