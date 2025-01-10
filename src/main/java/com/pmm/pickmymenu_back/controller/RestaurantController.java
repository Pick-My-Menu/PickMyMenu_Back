package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.restaurant.RestaurantReq;
import com.pmm.pickmymenu_back.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/saveInfo")
    public BaseResponse<Object> saveInfo(@RequestBody RestaurantReq req) {
        System.out.println(req);
        System.out.println("식당아이디: "+req.getId());
        System.out.println("레절트메뉴아이디: "+req.getResultMenuId());

        boolean result = restaurantService.saveInfo(req);
        return BaseResponse.success(result);
    }

}
