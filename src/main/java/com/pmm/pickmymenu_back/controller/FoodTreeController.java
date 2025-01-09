package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.FoodTreeDTO;
import com.pmm.pickmymenu_back.service.FoodTreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class FoodTreeController {

    private final FoodTreeService foodTreeService;

    @GetMapping("/random/parent")
    public ResponseEntity<List<FoodTreeDTO>> getTwoParentCategories(){
        List<FoodTreeDTO> result = foodTreeService.getTwoParentCategories();
        return ResponseEntity.ok(result);
    }


    @GetMapping("/random/child")
    public ResponseEntity<List<FoodTreeDTO>> getTwoChildCategoreis(@RequestParam Long parentId){
        List<FoodTreeDTO> child = foodTreeService.getTwoChildCategories(parentId);
        return ResponseEntity.ok(child);
    }

}
