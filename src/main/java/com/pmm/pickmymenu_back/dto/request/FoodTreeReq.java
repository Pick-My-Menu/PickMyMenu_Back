package com.pmm.pickmymenu_back.dto.request;

import com.pmm.pickmymenu_back.domain.FoodTree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodTreeReq {

    private Long id;
    private String category;
    private Integer level;
    private FoodTreeReq parent;

}
