package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.dto.response.admin.ChoicesRes;
import com.pmm.pickmymenu_back.dto.response.admin.MemberRes;
import com.pmm.pickmymenu_back.dto.response.admin.ReviewsRes;
import com.pmm.pickmymenu_back.dto.response.review.ReviewRes;
import com.pmm.pickmymenu_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // 관리자만 접근 가능
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberRes>> getMembers() {
        List<MemberRes> members = adminService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/choices")
    public ResponseEntity<List<ChoicesRes>> getChoices() {
        List<ChoicesRes> choices = adminService.getAllChoices();
        return ResponseEntity.ok(choices);
    }

    @GetMapping("/reviews")
    public ResponseEntity<Page<ReviewRes>> getReviews(Pageable pageable) {
        Page<ReviewRes> reviews = adminService.getAllReviews(pageable);
        return ResponseEntity.ok(reviews);
    }

}
