package com.pmm.pickmymenu_back.dto.request.survey;

import com.pmm.pickmymenu_back.dto.request.FoodTreeReq;
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
    private FoodTreeReq parentCategory;
    private FoodTreeReq childCategory;
    private String selectedKeyword;

}
