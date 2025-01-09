package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
