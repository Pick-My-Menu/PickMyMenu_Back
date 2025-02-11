package com.pmm.pickmymenu_back.global;

import com.pmm.pickmymenu_back.domain.Choice;
import com.pmm.pickmymenu_back.repository.ChoiceRepository;
import com.pmm.pickmymenu_back.repository.MemberRepository;

import com.pmm.pickmymenu_back.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class CustomRunner implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final ChoiceRepository choiceRepository;
    private final SurveyRepository surveyRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

//
//        surveyRepository.deleteAll();
//        choiceRepository.deleteAll();
//
//
//
//        List<Choice> choices = Arrays.asList(
//                Choice.create("매운 음식", "순한 음식"),
//                Choice.create("고기 요리", "채식 요리"),
//                Choice.create("밥", "면"),
//                Choice.create("국물 요리", "볶음 요리"),
//                Choice.create("달콤한 맛", "짭짤한 맛"),
//                Choice.create("간편식", "정성 가득한 요리"),
//                Choice.create("전통 한식", "현대 퓨전 음식"),
//                Choice.create("빠른 식사", "여유로운 식사"),
//                Choice.create("따뜻한 음식", "차가운 음식"),
//                Choice.create("해산물 요리", "육류 요리"),
//                Choice.create("자극적인 맛", "담백한 맛"),
//                Choice.create("간단한 식사", "풍성한 코스 요리"),
//                Choice.create("신선한 재료 요리", "풍미 소스 요리"),
//                Choice.create("새로운 음식 도전", "익숙한 메뉴"),
//                Choice.create("패스트푸드", "정통 요리"),
//                Choice.create("부드러운 식감", "바삭한 식감"),
//                Choice.create("신선한 재료 음식", "숙성된 재료 음식"),
//                Choice.create("한 종류 요리", "여러 종류 요리"),
//                Choice.create("깔끔한 플레이팅", "창의적인 플레이팅"),
//                Choice.create("단순한 맛", "복합적인 맛"),
//                Choice.create("건강 중시 음식", "맛 중시 음식")
//        );
//
//        choiceRepository.saveAll(choices);



//        Choice choice = Choice.create("편안한 분위기", "격식 있는 분위기");
//        Choice choice20 = Choice.create("실내 식사", "야외 식사");
//        Choice choice26 = Choice.create("빠른 식사", "여유로운 식사");
//        Choice choice23 = Choice.create("계절 음식", "사계절 음식");

//        Choice choice1 = Choice.create("부드러운 음식", "바삭한 음식");
//        Choice choice2 = Choice.create("뜨거운 음식", "차가운 음식");
//        Choice choice3 = Choice.create("육류 중심", "채소 중심");
//        Choice choice4 = Choice.create("짭짤한 맛", "담백한 맛");
//        Choice choice5 = Choice.create("매운 음식", "순한 음식");
//        Choice choice6 = Choice.create("향신료 많은 음식", "향신료 적은 음식");
//        Choice choice7 = Choice.create("국물 있는 음식", "마른 음식");
//        Choice choice8 = Choice.create("익힌 음식", "날것");
//        Choice choice9 = Choice.create("건강식", "맛 중심 식사");
//        Choice choice10 = Choice.create("전통적인 맛", "현대적인 맛");
//        Choice choice11 = Choice.create("손으로 먹는 음식", "수저로 먹는 음식");
//        Choice choice12 = Choice.create("다양한 색감", "단조로운 색감");
//        Choice choice13 = Choice.create("포만감 중시", "맛 중시");
//        Choice choice15 = Choice.create("가정식 스타일", "레스토랑 스타일");
//        Choice choice16 = Choice.create("육류 위주", "해산물 위주");
//        Choice choice17 = Choice.create("저칼로리 음식", "고칼로리 음식");
//        Choice choice21 = Choice.create("혼자 먹는 식사", "함께 먹는 식사");
//        Choice choice27 = Choice.create("알코올과 어울림", "음료와 어울림");
//        Choice choice28 = Choice.create("신선한 재료", "가공된 재료");
//        Choice choice29 = Choice.create("기름진 음식", "가벼운 음식");
//        Choice choice30 = Choice.create("향이 강한 음식", "향이 은은한 음식");
////
////
//        choiceRepository.save(choice1);
//        choiceRepository.save(choice2);
//        choiceRepository.save(choice3);
//        choiceRepository.save(choice4);
//        choiceRepository.save(choice5);
//        choiceRepository.save(choice6);
//        choiceRepository.save(choice7);
//        choiceRepository.save(choice8);
//        choiceRepository.save(choice9);
//        choiceRepository.save(choice10);
//        choiceRepository.save(choice11);
//        choiceRepository.save(choice12);
//        choiceRepository.save(choice13);
//        choiceRepository.save(choice15);
//        choiceRepository.save(choice16);
//        choiceRepository.save(choice17);
//        choiceRepository.save(choice21);
//        choiceRepository.save(choice27);
//        choiceRepository.save(choice28);
//        choiceRepository.save(choice29);
//        choiceRepository.save(choice30);



    }
}
