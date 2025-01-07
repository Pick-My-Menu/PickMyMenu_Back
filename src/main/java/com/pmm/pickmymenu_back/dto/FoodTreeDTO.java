package com.pmm.pickmymenu_back.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class FoodTreeDTO {

    private Long id;

    private String category;

    public FoodTreeDTO(Long id, String category) {
        this.id = id;
        this.category = category;
    }
}
