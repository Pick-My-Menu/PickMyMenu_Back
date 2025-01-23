package com.pmm.pickmymenu_back.dto.response.review;

import com.pmm.pickmymenu_back.domain.Review;
import lombok.Data;

@Data
public class ReviewCreateRes {

    private Long id;
    private String content;
    private int rating;

    public ReviewCreateRes(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.rating = review.getRating();
    }
}
