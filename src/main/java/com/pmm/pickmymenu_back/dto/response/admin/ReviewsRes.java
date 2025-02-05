package com.pmm.pickmymenu_back.dto.response.admin;

import com.pmm.pickmymenu_back.domain.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class ReviewsRes {

    private Long id;
    private String content;
    private int rating;
    private String reviewImageUrl;
    private String hiddenStatus;
    private String placeName;
    private String menu;
    private String email;

    public ReviewsRes(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.reviewImageUrl = review.getReviewImageUrl();
        this.hiddenStatus = String.valueOf(review.getHiddenStatus());

        // this.placeName = review.getResultMenu().getRestaurant().getPlaceName();
        // DB create-drop 후 지워볼 것
        this.placeName = Optional.ofNullable(review.getResultMenu())
                .map(ResultMenu::getRestaurant)
                .map(Restaurant::getPlaceName)
                .orElse("알 수 없음");
        this.menu = review.getResultMenu().getMenu();

        //this.email = review.getResultMenu().getMember().getEmail();
        // DB create-drop 후 지워볼 것
        this.email =Optional.ofNullable(review.getResultMenu())
                .map(ResultMenu::getMember)
                .map(Member::getEmail)
                .orElse("알 수 없음");

    }
}
