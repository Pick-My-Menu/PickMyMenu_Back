package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.FoodTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodTreeRepository extends JpaRepository<FoodTree, Long>  {

    @Query("SELECT f FROM FoodTree f  WHERE f.parent.id IS NULL  ")
    List<FoodTree> findAllByParentIsNull();

    List<FoodTree> findByParentId(Long parentId);
}
