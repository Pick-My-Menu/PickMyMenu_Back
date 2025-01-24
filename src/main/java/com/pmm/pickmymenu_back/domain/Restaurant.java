package com.pmm.pickmymenu_back.domain;

import com.pmm.pickmymenu_back.dto.request.restaurant.RestaurantReq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;  // 기본 키

    private Long resId;  // RestaurantId

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

    private Integer count;

    @OneToMany(mappedBy = "restaurant")
    private List<ResultMenu> resultMenus;


    public void incrementCount() {
        this.count += 1; // 카운트 + 1 메소드
    }


    public Restaurant(Long resId, String placeName, String addressName, String roadAddressName,
            String phone, String categoryGroupCode, String categoryGroupName, String categoryName,
            Double x, Double y, String placeUrl, String imageUrl, Integer distance) {
        this.resId = resId;
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
        this.count = 1;
    }

    public static Restaurant save(RestaurantReq req) {
        return new Restaurant(req.getResId(), req.getPlaceName(), req.getAddressName(),
                req.getRoadAddressName(), req.getPhone(), req.getCategoryGroupCode(),
                req.getCategoryGroupName(), req.getCategoryName(), req.getX(), req.getY(),
                req.getPlaceUrl(), req.getImageUrl(), req.getDistance());
    }

}
