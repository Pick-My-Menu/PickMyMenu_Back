package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.VSGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VSGameRepository extends JpaRepository<VSGame, Long> {

    @Query(value = "SELECT * FROM vsgame WHERE vs_group = :vs_group1 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    VSGame findRandomByGroupId(@Param("vs_group1") int vs_group1);


}