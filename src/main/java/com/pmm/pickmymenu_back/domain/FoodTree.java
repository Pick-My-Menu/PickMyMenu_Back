package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.*;
import java.util.List;
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

    @OneToMany(mappedBy = "parentFoodTree")
    private List<Survey> parentSurveyList;

    @OneToMany(mappedBy = "childFoodTree")
    private List<Survey> childSurveyList;

    private FoodTree(String category, Integer level, FoodTree parent) {
        this.category = category;
        this.level = level;
        this.parent = parent;
    }

    public static FoodTree create(String category, Integer level, FoodTree parent) {
        return new FoodTree(category, level, parent);
    }
}
