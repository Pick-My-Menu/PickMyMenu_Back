package com.pmm.pickmymenu_back.dto;

import com.pmm.pickmymenu_back.domain.FoodTree;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class FoodTreeDTO {

    private Long id;

    private String category;
    private Integer level;
    private FoodTree parent;

    public FoodTreeDTO(Long id, String category, Integer level, FoodTree parent) {
        this.id = id;
        this.category = category;
        this.level = level;
        this.parent = parent;
    }
}
