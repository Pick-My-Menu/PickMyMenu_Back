package com.pmm.pickmymenu_back.dto.request.member;

import com.pmm.pickmymenu_back.exception.MemberException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinReq {
    private String email;
    private String password;
    private String name;
    private String birthdate;
    private String phoneNumber;
    private String gender;


    public void setGender(String gender) {
        if (gender.equals("male")) {
            this.gender = "남";
        } else if (gender.equals("female")) {
            this.gender = "여";
        }else{
            throw new MemberException("성별 값이 잘못되었습니다");
        }
    }
}
