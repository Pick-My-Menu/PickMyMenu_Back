package com.pmm.pickmymenu_back.dto.request.review;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewCreateReq {

    private String content;
    private int rating;
    private Long restaurantId;
    private Long resultMenuId;
    private String reviewImageUrl;
    private MultipartFile ReviewImage;
}
