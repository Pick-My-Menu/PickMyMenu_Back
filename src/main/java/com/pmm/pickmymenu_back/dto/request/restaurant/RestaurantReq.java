package com.pmm.pickmymenu_back.dto.request.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RestaurantReq {

    private Long id; // 식당 id

    // 넘어오는 값은 snake_case인데, 서버에서 받아야되는 값은 camelCase라서 @JsonProperty 어노테이션 사용
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

    private Long resultMenuId;



}
