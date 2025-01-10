package com.pmm.pickmymenu_back.dto.request.survey;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SurveyCollectReq {
    private List<SurveyCollect> list;
    private String menu;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SurveyCollect{
        private Long id;
        private String question0;
        private String question1;
        private boolean selected;

        public String getSelected1() {
            return this.selected ? this.question1 : this.question0;
        }

        public String getNotSelected() {
            return this.selected ?  this.question0 : this.question1;
        }
    }
}
