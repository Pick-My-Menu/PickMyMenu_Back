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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

//    // 리뷰 데이터 전송을 받는 엔드포인트
//    @PostMapping("/create")
//    public BaseResponse<ReviewCreateRes> create(@RequestBody ReviewCreateReq req,
//            @CookieValue(value = "token", required = false) String token) {
//        return BaseResponse.success(reviewService.create(req, token));
//    }


    // 리뷰 데이터 전송을 받는 엔드포인트
    @PostMapping("/create")
    public BaseResponse<ReviewCreateRes> create(@ModelAttribute ReviewCreateReq req,
                                                @CookieValue(value = "token", required = false) String token) throws IOException {
        reviewService.handleImageUpload(req);
        return BaseResponse.success(reviewService.create(req, token));
    }


    // 전체 리뷰
    @GetMapping()
    public BaseResponse<Page<ReviewRes>> getList(Pageable pageable) {
        Page<ReviewRes> list = reviewService.getList(pageable);
        return BaseResponse.success(list);
    }

    // 내 리뷰
    @GetMapping("/myReview")
    public BaseResponse<Page<ReviewRes>> getMyList(Pageable pageable,
                                                   @CookieValue(value = "token", required = false) String token) {
        Page<ReviewRes> list = reviewService.getMyList(pageable, token);
        return BaseResponse.success(list);
    }

    // 작성되지 않은 내 리뷰 갯수 카운트
    @GetMapping("/count")
    public BaseResponse<Long> getCount(@CookieValue(value = "token", required = false) String token) {
        long count = reviewService.getCount(token);
        return BaseResponse.success(count);
    }

    // 리뷰 숨기기
    @PostMapping("/hide")
    public BaseResponse<ReviewRes> hideReview(
            @RequestParam Long resultMenuId,
            @CookieValue(value = "token", required = false) String token) {

        ReviewRes reviewRes = reviewService.hideReview(resultMenuId, token);
        System.out.println("숨깁니다");

        return BaseResponse.success(reviewRes);
    }

    // 리뷰 숨기기
    @PostMapping("/unHide")
    public BaseResponse<ReviewRes> unHideReview(
            @RequestParam Long resultMenuId,
            @CookieValue(value = "token", required = false) String token) {

        ReviewRes reviewRes = reviewService.unHideReview(resultMenuId, token);
        System.out.println("공개합니다");

        return BaseResponse.success(reviewRes);
    }

}
