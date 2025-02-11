package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.ResultMenu;
import com.pmm.pickmymenu_back.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 관리자용 모든 리뷰 조회
    Page<Review> findAllByOrderByCreatedDateDesc(Pageable pageable);

    // 사용자용 숨김 리뷰 조회
    @Query("SELECT r FROM Review r WHERE r.hiddenStatus = 0 and r.deletedStatus = 0")
    Page<Review> findAllByOrderByCreatedDateDesc2(Pageable pageable);

    // 내 리뷰 날짜순 조회
//    @Query("SELECT r FROM Review r WHERE r.deletedStatus = 0")
    Page<Review> findByResultMenu_MemberOrderByCreatedDateDesc(Pageable pageable, Member member);

    // 리뷰id로 작성된 리뷰 찾기
    Optional<Review> findByResultMenu(ResultMenu resultMenu);

}
