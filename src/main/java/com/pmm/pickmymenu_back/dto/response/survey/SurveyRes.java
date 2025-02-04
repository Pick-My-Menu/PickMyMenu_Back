package com.pmm.pickmymenu_back.dto.response.survey;

import com.pmm.pickmymenu_back.domain.Survey;
import lombok.Data;

@Data
public class SurveyRes {
    private Long surveyId;
    private String selected;
    private String notSelected;

    public SurveyRes(Survey survey) {
        this.surveyId = survey.getId();
        this.selected = survey.getSelected();
        this.notSelected = survey.getNotSelected();
    }
}
