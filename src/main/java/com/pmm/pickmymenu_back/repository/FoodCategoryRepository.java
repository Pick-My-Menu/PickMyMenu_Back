package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Integer> {

    @Query(value = "SELECT * FROM food_category ORDER BY RAND() LIMIT 2", nativeQuery = true)
    List<FoodCategory> findTwoRandomCategories();

}
