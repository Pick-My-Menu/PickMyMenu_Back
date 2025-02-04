package com.pmm.pickmymenu_back.dto.response.review;

import com.pmm.pickmymenu_back.domain.Review;
import com.pmm.pickmymenu_back.util.CustomUtil;
import lombok.Data;

@Data
public class ReviewRes {

    private Long id;
    private String content;
    private int rating;
    private String placeName;
    private String menu;
    private String email;
    private String createDate;
    private String reviewImageUrl;
    private int hiddenStatus;
    private int deletedStatus;
    private Long resultMenuId;

    public ReviewRes(Review review) {
        this.id = review.getId();
        this.content = review.getContent() != null ? review.getContent() : ""; // null 체크
        this.rating = review.getRating();
        this.placeName = review.getResultMenu() != null && review.getResultMenu().getRestaurant() != null
                ? review.getResultMenu().getRestaurant().getPlaceName() : ""; // null 체크
        this.menu = review.getResultMenu() != null
                ? review.getResultMenu().getMenu() : ""; // null 체크
        this.email = review.getResultMenu() != null && review.getResultMenu().getMember() != null
                ? review.getResultMenu().getMember().getEmail() : ""; // null 체크
        this.createDate = review.getCreatedDate() != null
                ? CustomUtil.formatter(review.getCreatedDate()) : ""; // null 체크
        this.reviewImageUrl = review.getReviewImageUrl() != null ? review.getReviewImageUrl() : "";
        this.hiddenStatus = review.getHiddenStatus(); // 숨기기, 삭제 기능때문에 추가
        this.deletedStatus = review.getDeletedStatus(); // 숨기기, 삭제 기능때문에 추가
        this.resultMenuId = review.getResultMenu().getId(); // 숨기기, 삭제 기능때문에 추가
    }

}
