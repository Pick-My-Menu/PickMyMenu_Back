package com.pmm.pickmymenu_back.dto;

import com.pmm.pickmymenu_back.domain.FoodCategory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FoodCategoryDTO {

    private Long id; // 카테고리 ID
    private String name; // 카테고리 이름

    public FoodCategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
}
