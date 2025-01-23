package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.Restaurant;
import com.pmm.pickmymenu_back.domain.ResultMenu;
import com.pmm.pickmymenu_back.domain.Review;
import com.pmm.pickmymenu_back.dto.request.review.ReviewReq;
import com.pmm.pickmymenu_back.dto.response.review.ReviewRes;
import com.pmm.pickmymenu_back.exception.MemberException;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import com.pmm.pickmymenu_back.repository.ReviewRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ResultMenuRepository resultMenuRepository;
    private final JWTUtil jwtUtil;


    public ReviewRes createReview(ReviewReq req, String token) {

        if (token == null) throw new MemberException("토큰이 존재하지 않습니다.");

        String email = jwtUtil.validateAndExtract(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("해당 사용자를 찾을 수 없습니다."));

        ResultMenu resultMenu = resultMenuRepository.findById(req.getResultMenuId())
                .orElseThrow(() -> new RuntimeException("일치하는 ResultMenu가 없습니다."));

        if (!resultMenu.getMember().getId().equals(member.getId())) {
            throw new RuntimeException("리뷰 작성 권한이 없습니다.");
        }

        // 이미 리뷰가 작성된 경우
        if ("1".equals(resultMenu.getIsReviewed())) {
            throw new RuntimeException("이미 리뷰가 작성된 상태입니다.");
        }

        Review review = Review.create(req.getContent(), req.getRating(), resultMenu);
        Review savedReview = reviewRepository.save(review);

        resultMenu.setReviewed();
        resultMenuRepository.save(resultMenu);

        return new ReviewRes(
                savedReview.getContent(),
                savedReview.getRating(),
                savedReview.getResultMenu().getRestaurant().getPlaceName(),
                savedReview.getResultMenu().getMenu(),
                savedReview.getCreatedDate()
        );
    }

}
