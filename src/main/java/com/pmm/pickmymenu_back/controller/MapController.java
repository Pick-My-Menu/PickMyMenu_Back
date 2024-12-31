package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.map.PlaceSearchReq;
import com.pmm.pickmymenu_back.dto.response.map.PlaceSearchRes;
import com.pmm.pickmymenu_back.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/map")
public class MapController {

    private final MapService mapService;

    @GetMapping("/placeSearch")
    public Mono<BaseResponse<PlaceSearchRes>> placeSearch(@ModelAttribute PlaceSearchReq req) {
        System.out.println(req);
        return mapService.placeSearch(req).map(BaseResponse::success);
//        return BaseResponse.success(res);
    }
}
