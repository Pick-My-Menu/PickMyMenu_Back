package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.Survey;
import com.pmm.pickmymenu_back.dto.response.rank.RankSurveyRes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {


//    @Query("SELECT s.choice.id, c.question0, COUNT(s.choice) - SUM(CAST(s.isSelected AS UNSIGNED)), "
//            + "c.question1, SUM(CAST(s.isSelected AS UNSIGNED)), COUNT(s.choice.id) AS totalCount  "
//            + "FROM Survey s "
//            + "LEFT JOIN FETCH s.choice c "
//            + "GROUP BY s.choice, s.isSelected "
//            + "ORDER BY totalCount")
//    Object[] findSurveyByChoiceId();

    @Query("SELECT new com.pmm.pickmymenu_back.dto.response.rank.RankSurveyRes("
            + "s.choice.id AS choiceId, "
            + "c.question0 AS question0, "
            + "(COUNT(s.choice) - SUM(CASE WHEN s.isSelected = true THEN 1 ELSE 0 END)) AS notSelectedCount, "
            + "c.question1 AS question1, "
            + "SUM(CASE WHEN s.isSelected = true THEN 1 ELSE 0 END) AS selectedCount, "
            + "COUNT(s.choice.id) AS totalCount) "
            + "FROM Survey s "
            + "LEFT JOIN s.choice c "
            + "GROUP BY s.choice.id, c.question0, c.question1 "
            + "ORDER BY totalCount DESC")
    List<RankSurveyRes> findSurveyByChoiceId();

}
