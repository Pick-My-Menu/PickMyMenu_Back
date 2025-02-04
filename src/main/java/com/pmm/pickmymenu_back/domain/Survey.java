package com.pmm.pickmymenu_back.domain;

import com.pmm.pickmymenu_back.dto.request.survey.SurveyCollectReq.SurveyCollect;
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
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Survey extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_group_id", nullable = false)
    private SurveyGroup surveyGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice_id", nullable = false)
    private Choice choice;

    @Column(nullable = false)
    private String selected;

    @Column(nullable = false)
    private String notSelected;

    @Column(nullable = false)
    private boolean isSelected;


    private Survey(SurveyGroup surveyGroup, Choice choice, String selected, String notSelected,
            boolean isSelected) {
        this.surveyGroup = surveyGroup;
        this.choice = choice;
        this.selected = selected;
        this.notSelected = notSelected;
        this.isSelected = isSelected;
    }

    public static Survey create(SurveyGroup surveyGroup, Choice choice, SurveyCollect surveyCollect) {
        return new Survey(surveyGroup, choice, surveyCollect.getSelected1(),
                surveyCollect.getNotSelected(), surveyCollect.isSelected());
    }
}
