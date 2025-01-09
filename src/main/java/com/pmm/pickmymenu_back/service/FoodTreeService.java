package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.FoodTree;
import com.pmm.pickmymenu_back.dto.FoodTreeDTO;
import com.pmm.pickmymenu_back.repository.FoodTreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodTreeService {

    private final FoodTreeRepository foodTreeRepository;

    public List<FoodTreeDTO> getTwoParentCategories() {
        List<FoodTree> twoCategories = foodTreeRepository.findAllByParentIsNull();

        Collections.shuffle(twoCategories); // 리스트 섞기

        return twoCategories.stream()
                            .limit(2) // 2개 제한
                            .map(f -> new FoodTreeDTO(f.getId(), f.getCategory(), f.getLevel(), f.getParent()))
                            .collect(Collectors.toList());
    }

    public List<FoodTreeDTO> getTwoChildCategories(Long parentId) {
        List<FoodTree> child = foodTreeRepository.findByParentId(parentId);

        Collections.shuffle(child);

        return child.stream()
                    .limit(2)
                    .map(child2 -> new FoodTreeDTO(child2.getId(), child2.getCategory(), child2.getLevel(), child2.getParent()))
                    .collect(Collectors.toList());
    }


}
