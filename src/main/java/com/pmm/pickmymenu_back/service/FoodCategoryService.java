package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.FoodCategoryDTO;
import com.pmm.pickmymenu_back.domain.FoodCategory;
import com.pmm.pickmymenu_back.repository.FoodCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    public List<FoodCategoryDTO> getRandomSelections(int groupNumber, int limit) {
        List<FoodCategory> categories = foodCategoryRepository.findRandomByGroup(groupNumber, limit);
        return categories.stream()
                .map(category -> new FoodCategoryDTO(category.getFoodCategoryId(), category.getCategoryName()))
                .collect(Collectors.toList());
    }
}
