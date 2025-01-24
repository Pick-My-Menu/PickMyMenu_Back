package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.restaurant.RestaurantReq;
import com.pmm.pickmymenu_back.dto.response.restaurant.RestaurantRes;
import com.pmm.pickmymenu_back.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/saveInfo")
    public BaseResponse<Object> saveInfo(@RequestBody RestaurantReq req) {
        boolean result = restaurantService.saveInfo(req);
        return BaseResponse.success(result);
    }


    // 방문식당 조회
    @GetMapping("/inquiry")
    public BaseResponse<List<RestaurantRes>> getResultMenusByToken(
            @CookieValue(value = "token", required = true) String token) {
//            @RequestHeader(value = "Authorization", required = true) String token) {
        List<RestaurantRes> resultMenus = restaurantService.getResultMenusByToken(token);
        return BaseResponse.success(resultMenus);
    }

}
