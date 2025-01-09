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
    private Long parent;

    public FoodTreeDTO(Long id, String category, Integer level, FoodTree parent) {
        this.id = id;
        this.category = category;
        this.level = level;
        if (parent == null) {
            this.parent = null;
        }else{
            this.parent = parent.getId();
        }
    }

    public static FoodTreeDTO createByFoodTree(FoodTree foodTree) {
        FoodTree parent1 = foodTree.getParent();
        if (parent1 == null) {
            return new FoodTreeDTO(foodTree.getId(), foodTree.getCategory(), foodTree.getLevel(), null);
        }
        return new FoodTreeDTO(foodTree.getId(), foodTree.getCategory(), foodTree.getLevel(), parent1);
    }
}
