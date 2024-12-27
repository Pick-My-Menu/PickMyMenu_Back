package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.FoodCategoryDTO;
import com.pmm.pickmymenu_back.domain.FoodCategory;
import com.pmm.pickmymenu_back.repository.FoodCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    public FoodCategoryDTO getRandomComparison() {
        List<FoodCategory> randomCategories = foodCategoryRepository.findTwoRandomCategories();

        if (randomCategories.size() < 2) {
            throw new IllegalStateException("Not enough categories in the database to create a comparison.");
        }

        return new FoodCategoryDTO(
                randomCategories.get(0).getCategoryName(),
                randomCategories.get(1).getCategoryName()
        );
    }

}
