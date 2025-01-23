package com.pmm.pickmymenu_back.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_result_id", nullable = false)
    private ResultMenu resultMenu;

    private Review(String content, int rating, ResultMenu resultMenu) {
        this.content = content;
        this.rating = rating;
        this.resultMenu = resultMenu;
    }

    public static Review create(String content, int rating, ResultMenu resultMenu){
        return new Review(content, rating, resultMenu);
    }

}