package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.SurveyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyGroupRepository extends JpaRepository<SurveyGroup, Long> {

}
