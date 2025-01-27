package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.ResultMenu;
import com.pmm.pickmymenu_back.dto.response.rank.RankMenuRes;
import com.pmm.pickmymenu_back.dto.response.rank.RankRestaurantRes;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResultMenuRepository extends JpaRepository<ResultMenu, Long> {
    List<ResultMenu> findByMemberOrderByCreatedDateDesc(Member member);


    @Query("SELECT new com.pmm.pickmymenu_back.dto.response.rank.RankMenuRes(r.menu, COUNT(r.menu)) "
            + "FROM ResultMenu r "
            + "WHERE (:time IS NULL OR r.createdDate > :time) "
            + "GROUP BY r.menu "
            + "ORDER BY COUNT(r.menu) DESC "
            + "LIMIT 10 ")
    List<RankMenuRes> findMenuCountsOrderByDesc(@Param("time") LocalDateTime time);

    @Query("SELECT COUNT(rm.restaurant) as resCount, rm.menu as menu, rt.placeName "
            + "FROM ResultMenu rm "
            + "RIGHT JOIN rm.restaurant rt "
            + "WHERE rm.menu = :menuName "
            + "GROUP BY rm.restaurant ")
    List<Map<String, Object>> findMenuByRestaurant(@Param("menuName") String menuName, @Param("time") LocalDateTime time);



    @Query("SELECT COUNT(rm) FROM ResultMenu rm WHERE rm.member = :member AND rm.isReviewed = '0' AND rm.restaurant IS NOT NULL")
    long count(@Param("member") Member member);

}
