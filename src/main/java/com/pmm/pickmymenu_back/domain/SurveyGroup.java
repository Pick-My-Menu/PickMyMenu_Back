package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyGroup extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_menu_id")
    private ResultMenu resultMenu;

    @OneToMany(mappedBy = "surveyGroup")
    private List<Survey> surveyList = new ArrayList<>();

    private SurveyGroup(Member member, ResultMenu resultMenu) {
        this.member = member;
        this.resultMenu = resultMenu;
    }

    public static SurveyGroup create(Member member, ResultMenu resultMenu) {
        return new SurveyGroup(member, resultMenu);
    }
}
