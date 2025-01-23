package com.pmm.pickmymenu_back.dto.response.review;

import com.pmm.pickmymenu_back.domain.ResultMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRes {
    private Long reviewId;
    private String content;
    private int rating;
    private String placeName;
    private String menu;
    private LocalDateTime createdDate;


    public ReviewRes(String content, int rating, String placeName, String menu, LocalDateTime createdDate) {
        this.content = content;
        this.rating = rating;
        this.placeName = placeName;
        this.menu = menu;
        this.createdDate = createdDate;
    }
}