package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    @Column(name = "menu", nullable = false)
    private String menu;

    private ResultMenu(Member member, String menu) {
        this.member = member;
        this.menu = menu;
    }

    public static ResultMenu create(Member member, String menu) {
        return new ResultMenu(member, menu);
    }
}
