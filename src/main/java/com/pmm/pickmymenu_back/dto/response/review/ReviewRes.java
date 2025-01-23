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

    public ReviewRes(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.placeName = review.getResultMenu().getRestaurant().getPlaceName();
        this.menu = review.getResultMenu().getMenu();
        this.email = review.getResultMenu().getMember().getEmail();
        this.createDate = CustomUtil.formatter(review.getCreatedDate());
    }
}
