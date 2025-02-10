package com.pmm.pickmymenu_back.service;


import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.Review;
import com.pmm.pickmymenu_back.dto.request.review.ReviewEditReq;
import com.pmm.pickmymenu_back.dto.response.admin.ChoicesRes;
import com.pmm.pickmymenu_back.dto.response.admin.MemberRes;
import com.pmm.pickmymenu_back.dto.response.admin.ReviewsRes;
import com.pmm.pickmymenu_back.dto.response.review.ReviewRes;
import com.pmm.pickmymenu_back.repository.ChoiceRepository;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import com.pmm.pickmymenu_back.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final ChoiceRepository choiceRepository;
    private final ReviewRepository reviewRepository;
    private final ResultMenuRepository resultMenuRepository;


    // 관리자용 모든 멤버 리스트
    public List<MemberRes> getAllMembers(){
        return memberRepository.findAllByRole("ROLE_USER").stream()
                .map(member -> {
                    int reviewCount = resultMenuRepository.reviewCountByMemberId(member.getId());
                    int restaurantCount = resultMenuRepository.restaurantCountByMemberId(member.getId());
                    return new MemberRes(member, reviewCount, restaurantCount);
                })
                .collect(Collectors.toList());
    }


    // 관리자용 모든 선택지 리스트
    public List<ChoicesRes> getAllChoices(){
        return choiceRepository.findAll().stream()
                .map(ChoicesRes::new)
                .collect(Collectors.toList());
    }


    // 관리자용 모든 리뷰 리스트
    public Page<ReviewRes> getAllReviews(Pageable pageable) {
        Page<Review> all = reviewRepository.findAllByOrderByCreatedDateDesc(pageable);
        List<ReviewRes> filteredReviews = all.getContent().stream()
                .map(ReviewRes::new)  // Review 객체를 ReviewRes로 변환
                .collect(Collectors.toList());
        return new PageImpl<>(filteredReviews, pageable, filteredReviews.size());
    }


    // 관리자용 리뷰 수정
    public Review updateReview(ReviewEditReq req) {
        Review review = reviewRepository.findById(req.getId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        review.editReview(req.getContent(), req.getRating(), req.getHiddenStatus());

        return reviewRepository.save(review);

    }


    // 관리자용 리뷰 삭제/복원
    public void changeReviewStatus(Long id, int status) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        review.deleteReview(status);

        reviewRepository.save(review);
    }



}
