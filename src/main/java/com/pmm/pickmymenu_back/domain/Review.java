package com.pmm.pickmymenu_back.domain;

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
    @JoinColumn(name = "menu_result_id")
    private ResultMenu resultMenu;

}