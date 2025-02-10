package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.Review;
import com.pmm.pickmymenu_back.dto.request.review.ReviewEditReq;
import com.pmm.pickmymenu_back.dto.response.admin.ChoicesRes;
import com.pmm.pickmymenu_back.dto.response.admin.MemberRes;
import com.pmm.pickmymenu_back.dto.response.admin.ReviewsRes;
import com.pmm.pickmymenu_back.dto.response.review.ReviewRes;
import com.pmm.pickmymenu_back.service.AdminService;
import com.pmm.pickmymenu_back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final JWTUtil jwtUtil;

    @GetMapping("/members")
    public ResponseEntity<List<MemberRes>> getMembers(@CookieValue(value = "token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }

        // 토큰에서 role 추출
        String role = jwtUtil.findRole(token);
        if ("ROLE_ADMIN".equals(role)) {
            List<MemberRes> members = adminService.getAllMembers();
            return ResponseEntity.ok(members);
        } else {
            throw new AccessDeniedException("접근 권한이 없습니다.");

        }
    }

    @GetMapping("/choices")
    public ResponseEntity<List<ChoicesRes>> getChoices(@CookieValue(value = "token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }

        // 토큰에서 role 추출
        String role = jwtUtil.findRole(token);
        if ("ROLE_ADMIN".equals(role)) {
            List<ChoicesRes> choices = adminService.getAllChoices();
            return ResponseEntity.ok(choices);
        } else {
            throw new AccessDeniedException("접근 권한이 없습니다.");


        }
    }

    @GetMapping("/reviews")
    public ResponseEntity<Page<ReviewRes>> getReviews(Pageable pageable, @CookieValue(value = "token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }

        // 토큰에서 role 추출
        String role = jwtUtil.findRole(token);
        if ("ROLE_ADMIN".equals(role)) {
            Page<ReviewRes> reviews = adminService.getAllReviews(pageable);
            return ResponseEntity.ok(reviews);
        } else {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

    }

    // 관리자용 리뷰 수정
    @PostMapping("/review/edit")
    public ResponseEntity<Review> updateReview(@RequestBody ReviewEditReq req) {
        try {
            Review updatedReview = adminService.updateReview(req);
            return ResponseEntity.ok(updatedReview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    // 관리자용 리뷰 삭제/복원
    @PostMapping("/review/changeStatus")
    public ResponseEntity<String> changeReviewStatus(@RequestParam Long id, @RequestParam int status) {
        try {
            adminService.changeReviewStatus(id, status);
            return ResponseEntity.ok(status == 1 ? "리뷰가 삭제되었습니다." : "리뷰가 복원되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 상태 변경 중 오류가 발생했습니다.");
        }
    }



}