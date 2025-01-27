package com.pmm.pickmymenu_back.domain;

import com.pmm.pickmymenu_back.dto.request.review.ReviewCreateReq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private String content;  // 리뷰 내용

    @Column(nullable = false)
    private int rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_result_id", nullable = false)
    private ResultMenu resultMenu;

    private String reviewImageUrl;

    public Review(String content, int rating, ResultMenu resultMenu, String reviewImageUrl) {
        this.content = content;
        this.rating = rating;
        this.resultMenu = resultMenu;
        this.reviewImageUrl = reviewImageUrl;
    }

    public static Review create(ReviewCreateReq req, ResultMenu resultMenu) {
        return new Review(req.getContent(), req.getRating(), resultMenu, req.getReviewImageUrl());
    }
}