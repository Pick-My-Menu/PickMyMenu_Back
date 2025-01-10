package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.Restaurant;
import com.pmm.pickmymenu_back.domain.ResultMenu;
import com.pmm.pickmymenu_back.dto.request.restaurant.RestaurantReq;
import com.pmm.pickmymenu_back.repository.RestaurantRepository;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ResultMenuRepository resultMenuRepository;

    public boolean saveInfo(RestaurantReq req) {

        Restaurant existingRestaurant = restaurantRepository.findAllById(req.getId());

        if (existingRestaurant != null) {
            existingRestaurant.incrementCount();
            restaurantRepository.save(existingRestaurant);

            ResultMenu resultMenu = resultMenuRepository.findById(req.getResultMenuId()).orElseThrow(() -> new RuntimeException("일치하는 ResultMenu가 없습니다."));
            resultMenu.setRestaurant(existingRestaurant);
            resultMenuRepository.save(resultMenu);
            return true;
        }

        Restaurant restaurant = Restaurant.save(req.getId(), req.getPlaceName(), req.getAddressName(), req.getRoadAddressName(), req.getPhone(),
                req.getCategoryGroupCode(), req.getCategoryGroupName(), req.getCategoryName(),
                req.getX(), req.getY(), req.getPlaceUrl(), req.getImageUrl(), req.getDistance(), req.getCount());
        restaurantRepository.save(restaurant);

        ResultMenu resultMenu = resultMenuRepository.findById(req.getResultMenuId()).orElseThrow(() -> new RuntimeException("일치하는 ResultMenu가 없습니다."));
        resultMenu.setRestaurant(restaurant);
        resultMenuRepository.save(resultMenu);

        return true;
    }
}
