package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.Restaurant;
import com.pmm.pickmymenu_back.domain.ResultMenu;
import com.pmm.pickmymenu_back.dto.request.restaurant.RestaurantReq;
import com.pmm.pickmymenu_back.dto.response.restaurant.RestaurantRes;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.repository.RestaurantRepository;
import com.pmm.pickmymenu_back.repository.ResultMenuRepository;

import java.util.List;
import java.util.Optional;

import com.pmm.pickmymenu_back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ResultMenuRepository resultMenuRepository;
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    public boolean saveInfo(RestaurantReq req) {

        Optional<Restaurant> existingRestaurant = restaurantRepository.findByResId(req.getResId());

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


    // 방문식당 조회
    public List<RestaurantRes> getResultMenusByToken(String token) {
        // 1. 토큰 검증 및 이메일 추출
        String email = jwtUtil.validateAndExtract(token);



        // 2. 이메일로 회원 정보 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));

        // 3. ResultMenu 테이블에서 member와 일치하는 데이터 조회
        List<ResultMenu> resultMenus = resultMenuRepository.findByMember(member);
        // 4. ResultMenu 엔티티를 ResultMenuResDTO로 변환하여 반환
        return resultMenus.stream()
                .map(RestaurantRes::fromEntity)
                .toList();
    }


}
