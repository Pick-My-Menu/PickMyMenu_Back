package com.pmm.pickmymenu_back.dto.request.review;

import lombok.*;


public class ReviewRequest {

    private String content;  // content -> reviewText
    private int rating;         // ratingCount -> rating
    private Long id;   // idx -> placeName

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReviewRequest{" +
                "content='" + content + '\'' +
                ", rating=" + rating +
                ", id='" + id + '\'' +
                '}';
    }
}
