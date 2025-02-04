package com.pmm.pickmymenu_back.dto.response.member;

import com.pmm.pickmymenu_back.controller.RankController;
import com.pmm.pickmymenu_back.domain.SurveyGroup;
import com.pmm.pickmymenu_back.dto.response.survey.SurveyRes;
import com.pmm.pickmymenu_back.util.CustomUtil;
import java.util.List;
import lombok.Data;

@Data
public class MemberRecordRes {

    private List<RecordSurveyGroupRes> recordSurveyGroupResList;

    public MemberRecordRes(List<RecordSurveyGroupRes> recordSurveyGroupResList) {
        this.recordSurveyGroupResList = recordSurveyGroupResList;
    }

    @Data
    public static class RecordSurveyGroupRes{

        private Long id;
        private String menu;
        private String createdDate;
        private String restaurantName;
        private List<SurveyRes> surveyResList;

        public RecordSurveyGroupRes(SurveyGroup surveyGroup) {
            this.id = surveyGroup.getId();
            this.menu = surveyGroup.getResultMenu().getMenu();
            this.createdDate = CustomUtil.formatter(surveyGroup.getCreatedDate());
            this.surveyResList = surveyGroup.getSurveyList().stream().map(SurveyRes::new).toList();
            this.restaurantName = surveyGroup.getResultMenu().getRestaurant() == null ? "" :
                    surveyGroup.getResultMenu().getRestaurant().getPlaceName();
        }
    }
}
