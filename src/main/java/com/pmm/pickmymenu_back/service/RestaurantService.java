package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.Restaurant;
import com.pmm.pickmymenu_back.domain.ResultMenu;
import com.pmm.pickmymenu_back.dto.request.restaurant.RestaurantReq;
import com.pmm.pickmymenu_back.repository.RestaurantRepository;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ResultMenuRepository resultMenuRepository;

    public boolean saveInfo(RestaurantReq req) {

        Optional<Restaurant> existingRestaurant = restaurantRepository.findById(req.getId());

        if (existingRestaurant.isPresent()) {
            Restaurant restaurant = existingRestaurant.get();
            restaurant.incrementCount();
            restaurantRepository.save(restaurant);

            ResultMenu resultMenu = resultMenuRepository.findById(req.getResultMenuId())
                    .orElseThrow(() -> new RuntimeException("일치하는 ResultMenu가 없습니다."));
            resultMenu.setRestaurant(restaurant);
            resultMenuRepository.save(resultMenu);
            return true;
        }

        Restaurant restaurant = Restaurant.save(req);
        restaurantRepository.save(restaurant);

        ResultMenu resultMenu = resultMenuRepository.findById(req.getResultMenuId())
                .orElseThrow(() -> new RuntimeException("일치하는 ResultMenu가 없습니다."));
        resultMenu.setRestaurant(restaurant);
        resultMenuRepository.save(resultMenu);

        return true;
    }
}
