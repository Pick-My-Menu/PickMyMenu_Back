package com.pmm.pickmymenu_back.dto.response.admin;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.util.CustomUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRes {

    private Long id;
    private String email;
    private String name;
    private String birthdate;
    private String phoneNumber;
    private String gender;
    private String createdDate;
    private int review;
    private int restaurant;

    public MemberRes(Member member, int reviewCount, int restaurantCount) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.birthdate = member.getBirthdate();
        this.phoneNumber = member.getPhoneNumber();
        this.gender = member.getGender();
        this.createdDate = CustomUtil.formatter(member.getCreatedDate());
        this.review = reviewCount;
        this.restaurant = restaurantCount;
    }

}

