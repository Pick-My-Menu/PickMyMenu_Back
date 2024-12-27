package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.FoodCategoryDTO;
import com.pmm.pickmymenu_back.service.FoodCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FoodCategoryController {

    private final FoodCategoryService foodCategoryService;

    @GetMapping("/api/compare")
    public ResponseEntity<FoodCategoryDTO> getRandomComparison() {
        FoodCategoryDTO comparison = foodCategoryService.getRandomComparison();
        return ResponseEntity.ok(comparison);
    }


}
