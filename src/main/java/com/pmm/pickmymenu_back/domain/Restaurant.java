package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId; // 테이블 id

    private Long id; // 식당의 id

    private String placeName;

    private String addressName;

    private String roadAddressName;

    private String phone;

    private String categoryGroupCode;

    private String categoryGroupName;

    private String categoryName;

    private Double x;

    private Double y;

    private String placeUrl;

    private String imageUrl;

    private Integer distance;

    private int count = 0;

    @OneToMany(mappedBy = "restaurant")
    private List<ResultMenu> resultMenus;

    public void incrementCount() {
        this.count += 1; // 카운트 + 1 메소드
    }


    public Restaurant(Long id, String placeName, String addressName, String roadAddressName, String phone, String categoryGroupCode, String categoryGroupName, String categoryName, Double x, Double y, String placeUrl, String imageUrl, Integer distance, int count) {
        this.id = id;
        this.placeName = placeName;
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
        this.phone = phone;
        this.categoryGroupCode = categoryGroupCode;
        this.categoryGroupName = categoryGroupName;
        this.categoryName = categoryName;
        this.x = x;
        this.y = y;
        this.placeUrl = placeUrl;
        this.imageUrl = imageUrl;
        this.distance = distance;
        this.count = count;
    }

    public static Restaurant save(Long id, String placeName, String addressName, String roadAddressName, String phone, String categoryGroupCode, String categoryGroupName, String categoryName, Double x, Double y, String placeUrl, String imageUrl, Integer distance, int count) {
        return new Restaurant(id, placeName, addressName, roadAddressName, phone, categoryGroupCode, categoryGroupName, categoryName, x, y, placeUrl, imageUrl, distance, count);
    }

}
