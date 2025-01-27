package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.ResultMenu;
import com.pmm.pickmymenu_back.domain.Review;
import com.pmm.pickmymenu_back.dto.request.review.ReviewCreateReq;
import com.pmm.pickmymenu_back.dto.request.review.ReviewReq;
import com.pmm.pickmymenu_back.dto.response.review.ReviewCreateRes;
import com.pmm.pickmymenu_back.dto.response.review.ReviewRes;
import com.pmm.pickmymenu_back.exception.MemberException;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.repository.RestaurantRepository;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import com.pmm.pickmymenu_back.repository.ReviewRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;
    private final ResultMenuRepository resultMenuRepository;
    private final JWTUtil jwtUtil;

    public ReviewCreateRes create(ReviewCreateReq req, String token) {
        if (req.getContent().isEmpty() || req.getContent() == null) {
            throw new RuntimeException("내용을 작성해주세요.");
        }
        if (token == null) throw new MemberException("토큰이 존재하지 않습니다.");
        String email = jwtUtil.validateAndExtract(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("해당 사용자를 찾을 수 없습니다."));

        ResultMenu resultMenu = resultMenuRepository.findById(req.getResultMenuId())
                .orElseThrow(() -> new RuntimeException("해당 레스토랑 아이디를 찾을수 없습니다."));
        if ("1".equals(resultMenu.getIsReviewed())) {
            throw new RuntimeException("이미 리뷰가 작성된 상태입니다.");
        }
        if (!member.getId().equals(resultMenu.getMember().getId())) {
            throw new MemberException("해당 레스토랑을 방문한 사용자가 아닙니다.");
        }

        Review review = Review.create(req, resultMenu);
        reviewRepository.save(review);

        resultMenu.setReviewed();
        resultMenuRepository.save(resultMenu);

        return new ReviewCreateRes(review);
    }


    @Transactional(readOnly = true)
    public Page<ReviewRes> getList(Pageable pageable) {
        Page<Review> all = reviewRepository.findAll(pageable);
        return all.map(ReviewRes::new);
    }
}
