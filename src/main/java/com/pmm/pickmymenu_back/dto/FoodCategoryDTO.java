package com.pmm.pickmymenu_back.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FoodCategoryDTO {

    private String option1;
    private String option2;

    public FoodCategoryDTO(String option1, String option2) {
        this.option1 = option1;
        this.option2 = option2;
    }

}
