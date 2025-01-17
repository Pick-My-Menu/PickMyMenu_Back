package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.ResultMenu;
import com.pmm.pickmymenu_back.dto.response.rank.RankMenuRes;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultMenuRepository extends JpaRepository<ResultMenu, Long> {

    @Query("SELECT new com.pmm.pickmymenu_back.dto.response.rank.RankMenuRes(r.menu, COUNT(r.menu)) "
            + "FROM ResultMenu r "
            + "WHERE (:time IS NULL OR r.createdDate > :time) "
            + "GROUP BY r.menu "
            + "ORDER BY COUNT(r.menu) DESC "
            + "LIMIT 10 ")
    List<RankMenuRes> findMenuCountsOrderByDesc(@Param("time") LocalDateTime time);
}
