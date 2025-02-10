package com.pmm.pickmymenu_back.dto.request.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewEditReq {

    private Long id;
    private String content;
    private int rating;
    private int hiddenStatus;

}
