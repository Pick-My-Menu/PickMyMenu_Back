package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.Choice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM choice c " +
                    "WHERE is_use = true " +
                    "AND c.choice_id NOT IN :choiceIdList " +
                    "ORDER BY RAND() " +
                    "LIMIT 1"
    )
    Optional<Choice> getRandom(@Param("choiceIdList") List<Long> choiceIdList);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM choice c " +
                    "WHERE is_use = true " +
                    "ORDER BY RAND() " +
                    "LIMIT 1")
    Optional<Choice> getRandom();


    // 랜덤으로 선택지 3개 가져오기
    @Query(value = "SELECT * FROM choice ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Choice> findRandomChoices();

}
