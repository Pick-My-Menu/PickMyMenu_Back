package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.request.review.ReviewRequest;
import com.pmm.pickmymenu_back.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    // 리뷰 데이터 전송을 받는 엔드포인트
    @PostMapping("/create")
    public String submitReview(@RequestBody ReviewRequest reviewRequest) {
        // 요청 받은 데이터 출력
        logger.info("Review Received: {}", reviewRequest);

        // 받은 데이터를 그대로 반환하여 확인
        return "Received review for " + reviewRequest.getId();
    }

}
