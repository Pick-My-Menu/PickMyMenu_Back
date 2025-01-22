package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT COUNT(r) FROM ResultMenu r WHERE r.member.id = :memberId AND r.isReviewed = '0'")
    long countUnreviewedMenusByMemberId(@Param("memberId") Long memberId);

}
