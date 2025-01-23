package com.pmm.pickmymenu_back.dto.request.review;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewReq {

    private String content;
    private int rating;
    private Long restaurantId;
    private Long resultMenuId;

}
