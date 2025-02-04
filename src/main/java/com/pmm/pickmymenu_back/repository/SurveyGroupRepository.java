package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.SurveyGroup;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyGroupRepository extends JpaRepository<SurveyGroup, Long> {

    @Query("SELECT sg "
            + "FROM SurveyGroup sg "
            + "JOIN FETCH sg.surveyList "
            + "WHERE sg.member = :member "
            + "ORDER BY sg.id DESC")
    List<SurveyGroup> findGroupByMemberAndGetMenu(@Param("member") Member member);

}
