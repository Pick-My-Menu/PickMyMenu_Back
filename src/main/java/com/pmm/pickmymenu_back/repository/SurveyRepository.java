package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("SELECT ft.category, COUNT(s) FROM Survey s " +
            "JOIN s.parentFoodTree ft " +
            "GROUP BY ft.category " +
            "ORDER BY COUNT(s) DESC")
    List<Object[]> countParentFoodTreeCategory();

}
