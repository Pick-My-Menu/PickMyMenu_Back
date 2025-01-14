package com.pmm.pickmymenu_back.dto.response.member;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.util.CustomUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberMyPageRes {

    private Long id;          // 회원 번호 (고유 식별자)

    private String name;      // 사용자 이름

    private String password;  // 비밀번호 (암호화된 비밀번호)

    private String email;     // 이메일 (로그인 및 회원가입 시 사용)

    private String phoneNumber; // 전화번호

    private String birthdate; // 생년월일

    private String gender;    // 성별

    private String createdDate;  // 가입 날짜

    public MemberMyPageRes(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.password = member.getPassword();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.birthdate = member.getBirthdate();
        this.gender = member.getGender();
        this.createdDate = CustomUtil.formatter(member.getCreatedDate());
    }
}
