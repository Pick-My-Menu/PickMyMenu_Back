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

    @Column(nullable = false)
    private int hiddenStatus = 0; // 0 - 기본, 1 - 숨김

    @Column(nullable = false)
    private int deletedStatus = 0; // 0 - 기본, 1 - 삭제


    public Review(String content, int rating, ResultMenu resultMenu, String reviewImageUrl, int hiddenStatus, int deletedStatus) {
        this.content = content;
        this.rating = rating;
        this.resultMenu = resultMenu;
        this.reviewImageUrl = reviewImageUrl;
        this.hiddenStatus = hiddenStatus;
        this.deletedStatus = deletedStatus;
    }

    public static Review create(ReviewCreateReq req, ResultMenu resultMenu) {
        return new Review(req.getContent(), req.getRating(), resultMenu, req.getReviewImageUrl(), 0, 0);
    }

    public void editReview(String content, int rating, int hiddenStatus) {
        this.content = content;
        this.rating = rating;
        this.hiddenStatus = hiddenStatus;
    }

    public void deleteReview(int deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public void setHiddenStatus(int hiddenStatus) {
        this.hiddenStatus = hiddenStatus;
    }
}