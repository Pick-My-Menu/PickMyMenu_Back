package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Integer> {

    @Query(value = "SELECT * FROM food_category WHERE group_number = :groupNumber ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<FoodCategory> findRandomByGroup(@Param("groupNumber") int groupNumber, @Param("limit") int limit);
}
