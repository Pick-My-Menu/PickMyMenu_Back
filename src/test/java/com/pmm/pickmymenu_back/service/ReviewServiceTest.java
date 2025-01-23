package com.pmm.pickmymenu_back.service;

import static org.junit.jupiter.api.Assertions.*;

import com.pmm.pickmymenu_back.dto.response.review.ReviewRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("dev")
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Test
    void name() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ReviewRes> list = reviewService.getList(pageRequest);
        list.forEach(System.out::println);
    }
}