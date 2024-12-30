package com.pmm.pickmymenu_back.dto.request.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PlaceSearchReq {
    private String query; //검색어
//    private String categoryGroupCode = "FD6";
    private String x;
    private String y;
    @Builder.Default
    private int radius = 500;
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 15;
    @Builder.Default
    private String sort = "distance";

}
