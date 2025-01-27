package com.pmm.pickmymenu_back.dto.response.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pmm.pickmymenu_back.domain.Restaurant;
import com.pmm.pickmymenu_back.domain.ResultMenu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class RestaurantRes {

    private Long id;

    private Long resId;

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("road_address_name")
    private String roadAddressName;

    private String phone;

    @JsonProperty("category_group_code")
    private String categoryGroupCode;

    @JsonProperty("category_group_name")
    private String categoryGroupName;

    @JsonProperty("category_name")
    private String categoryName;

    private Double x;
    private Double y;

    @JsonProperty("place_url")
    private String placeUrl;

    @JsonProperty("image_url")
    private String imageUrl;

    private Integer distance;

    @JsonProperty("result_menu_id")
    private Long resultMenuId;

    private String menu;

    private String isReviewed;

    private String createdDate;


    public RestaurantRes(Long id, Long resId, String placeName, String addressName, String roadAddressName,
                         String phone, String categoryGroupCode, String categoryGroupName, String categoryName,
                         Double x, Double y, String placeUrl, String imageUrl, Integer distance, Long resultMenuId, String menu, String isReviewed, String createdDate) {
        this.id = id;
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
        this.resultMenuId = resultMenuId;
        this.menu = menu;
        this.isReviewed = isReviewed;
        this.createdDate = createdDate;
    }



    public static RestaurantRes fromEntity(ResultMenu resultMenu) {
        Restaurant restaurant = resultMenu.getRestaurant();  // 한 번만 호출

        return new RestaurantRes(
                Optional.ofNullable(restaurant).map(Restaurant::getId).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getResId).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getPlaceName).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getAddressName).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getRoadAddressName).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getPhone).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getCategoryGroupCode).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getCategoryGroupName).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getCategoryName).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getX).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getY).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getPlaceUrl).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getImageUrl).orElse(null),
                Optional.ofNullable(restaurant).map(Restaurant::getDistance).orElse(null),
                resultMenu.getId(), // ResultMenu의 ID를 매핑
                resultMenu.getMenu(),
                resultMenu.getIsReviewed(),
                resultMenu.getCreatedDate() != null ? resultMenu.getCreatedDate().toLocalDate().toString() : null // "yyyy-MM-dd"로 변환
        );

    }



}
