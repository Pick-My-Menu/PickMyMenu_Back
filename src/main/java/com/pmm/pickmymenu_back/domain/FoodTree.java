package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자는 JPA를 위해 protected로 제한
public class FoodTree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_tree_id")
    private Long id; // 기본 키 필드

    private String category;

    private Integer level;

    @ManyToOne
    @JoinColumn(name = "parent_food_tree_id")
    private FoodTree parent;


}
