package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.review.ReviewCreateReq;
import com.pmm.pickmymenu_back.dto.response.PageRes;
import com.pmm.pickmymenu_back.dto.response.review.ReviewCreateRes;
import com.pmm.pickmymenu_back.dto.response.review.ReviewRes;
import com.pmm.pickmymenu_back.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    // 리뷰 데이터 전송을 받는 엔드포인트
    @PostMapping("/create")
    public BaseResponse<ReviewCreateRes> create(@RequestBody ReviewCreateReq req,
            @CookieValue(value = "token", required = false) String token) {
        return BaseResponse.success(reviewService.create(req, token));
    }

    @GetMapping()
    public BaseResponse<PageRes<Page<ReviewRes>>> getList(Pageable pageable) {
        Page<ReviewRes> list = reviewService.getList(pageable);
        return BaseResponse.success(new PageRes<>(list));
    }

}
