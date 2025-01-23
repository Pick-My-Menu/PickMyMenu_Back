package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.review.ReviewReq;
import com.pmm.pickmymenu_back.dto.response.review.ReviewRes;
import com.pmm.pickmymenu_back.exception.MemberException;
import com.pmm.pickmymenu_back.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;


    // 리뷰 데이터 전송을 받는 엔드포인트
    @PostMapping("/create")
    public BaseResponse<Object> createReview(
            @RequestBody ReviewReq req,
            @CookieValue(value = "token", required = false) String token) {
        try {
            ReviewRes reviewRes = reviewService.createReview(req, token);
            return BaseResponse.success(reviewRes);
        } catch (MemberException e) {
            return BaseResponse.fail("사용자 인증 실패: " + e.getMessage());
        } catch (RuntimeException e) {
            return BaseResponse.fail("리뷰 저장 중 오류 발생: " + e.getMessage());
        }
    }

}
