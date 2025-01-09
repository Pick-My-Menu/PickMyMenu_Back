package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_menu_id")
    private ResultMenu resultMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_food_tree_id", nullable = false) // name 은 컬럼이름임
    private FoodTree parentFoodTree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_food_tree_id", nullable = false) // name 은 컬럼이름임
    private FoodTree childFoodTree;

    private Survey(Member member, ResultMenu resultMenu, FoodTree parentFoodTree,
            FoodTree childFoodTree) {
        this.member = member;
        this.resultMenu = resultMenu;
        this.parentFoodTree = parentFoodTree;
        this.childFoodTree = childFoodTree;
    }

    public static Survey create(Member member, ResultMenu resultMenu, FoodTree parentFoodTree,
            FoodTree childFoodTree) {
        return new Survey(member, resultMenu, parentFoodTree, childFoodTree);
    }

}
