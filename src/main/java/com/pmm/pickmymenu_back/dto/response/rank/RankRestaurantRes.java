package com.pmm.pickmymenu_back.dto.response.rank;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RankRestaurantRes {
    private Long resCount;
    private String name;
    private String placeName;
    private Long resId;
    private String placeUrl;
    private String roadAddressName;
    private Double startCount;


    public RankRestaurantRes(Long resCount, String name, String placeName, Long resId,
            String placeUrl,
            String roadAddressName) {
        this.resCount = resCount;
        this.name = name;
        this.placeName = placeName;
        this.resId = resId;
        this.placeUrl = placeUrl;
        this.roadAddressName = roadAddressName;
    }

    public RankRestaurantRes(Long resCount, String name, String placeName, Long resId,
            String placeUrl, String roadAddressName, Double startCount) {
        this.resCount = resCount;
        this.name = name;
        this.placeName = placeName;
        this.resId = resId;
        this.placeUrl = placeUrl;
        this.roadAddressName = roadAddressName;
        this.startCount = startCount == null ? 0 : startCount;
    }
}
