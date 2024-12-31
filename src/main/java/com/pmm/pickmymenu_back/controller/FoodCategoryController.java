package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.domain.FoodCategory;
import com.pmm.pickmymenu_back.dto.FoodCategoryDTO;
import com.pmm.pickmymenu_back.service.FoodCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FoodCategoryController {

    private final FoodCategoryService foodCategoryService;

    @GetMapping("/group1/random")
    public ResponseEntity<Map<String, Object>> getGroup1Selections(@RequestParam(required = false) String previousSelections) {
        List<FoodCategoryDTO> selections = foodCategoryService.getRandomSelections(1, 2);
        Map<String, Object> response = new HashMap<>();
        response.put("group", "group1");
        response.put("previous", previousSelections);
        response.put("selected", selections);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/group2/random")
    public ResponseEntity<Map<String, Object>> getGroup2Selections(@RequestParam(required = false) String previousSelections) {
        List<FoodCategoryDTO> selections = foodCategoryService.getRandomSelections(2, 2);
        Map<String, Object> response = new HashMap<>();
        response.put("group", "group2");
        response.put("previous", previousSelections);
        response.put("selected", selections);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/group3/random")
    public ResponseEntity<Map<String, Object>> getGroup3Selections(@RequestParam(required = false) String previousSelections) {
        List<FoodCategoryDTO> selections = foodCategoryService.getRandomSelections(3, 2);
        Map<String, Object> response = new HashMap<>();
        response.put("group", "group3");
        response.put("previous", previousSelections);
        response.put("selected", selections);
        return ResponseEntity.ok(response);
    }


}
