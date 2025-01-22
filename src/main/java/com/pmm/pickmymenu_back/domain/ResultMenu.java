package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ResultMenu 엔티티 클래스
 *
 * - JPA를 사용하여 데이터베이스 테이블과 매핑
 * - 설문조사 그룹(surveyGroup)과 회원(member)의 결과 메뉴(menu)를 저장
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultMenu extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_result_id")
    private Long id; // 기본 키 필드

    @OneToOne(mappedBy = "resultMenu")
    private SurveyGroup surveyGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    @Column(name = "menu", nullable = false)
    private String menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(mappedBy = "resultMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    private String isReviewed = "0";

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setSurveyGroup(SurveyGroup surveyGroup) {
        this.surveyGroup = surveyGroup;
    }

    public void setReviewed(String reviewed) {
        this.isReviewed = reviewed;
    }



    private ResultMenu(Member member, String menu) {
        this.member = member;
        this.menu = menu;
    }

    public static ResultMenu create(Member member, String menu) {
        return new ResultMenu(member, menu);
    }





}
