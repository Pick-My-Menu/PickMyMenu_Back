package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ResultMenu 엔티티 클래스
 * 
 * - JPA를 사용하여 데이터베이스 테이블과 매핑
 * - 설문조사 그룹(surveyGroup)과 회원(member)의 결과 메뉴(menu)를 저장
 */
@Entity // JPA가 관리하는 엔티티 클래스임을 명시
@Getter // 필드에 대한 Getter 메서드 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자는 JPA를 위해 protected로 제한
public class ResultMenu extends TimeEntity {

    @Id // 기본 키(PK)로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 AUTO_INCREMENT 전략을 사용
    @Column(name = "menu_result") // 테이블의 컬럼 이름을 지정
    private Long id; // 기본 키 필드

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계를 매핑하며 지연 로딩 전략을 사용
    @JoinColumn(name = "survey_group_id", nullable = false) // 외래 키로 매핑
    private SurveyGroup surveyGroup; // 설문조사 그룹을 참조하는 필드

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계를 매핑하며 지연 로딩 전략을 사용
    @JoinColumn(name = "member_id", nullable = false) // 외래 키로 매핑
    private Member member; // 회원 정보를 참조하는 필드

    @Column(name = "menu", nullable = false) // 테이블의 컬럼으로 매핑
    private String menu; // 결과 메뉴를 저장하는 필드

    /**
     * 정적 팩토리 메서드
     * 
     * @param surveyGroup 설문조사 그룹
     * @param member 회원
     * @param menu 결과 메뉴
     * @return 생성된 ResultMenu 객체
     */
    public static ResultMenu of(SurveyGroup surveyGroup, Member member, String menu) {
        ResultMenu resultMenu = new ResultMenu();
        resultMenu.surveyGroup = surveyGroup;
        resultMenu.member = member;
        resultMenu.menu = menu;
        return resultMenu;
    }
}
