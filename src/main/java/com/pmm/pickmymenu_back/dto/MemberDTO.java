package com.pmm.pickmymenu_back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long id;          // 회원 번호 (고유 식별자)

    private String name;               // 사용자 이름

    private String password;           // 비밀번호 (암호화된 비밀번호)

    private String email;              // 이메일 (로그인 및 회원가입 시 사용)

    private String phoneNumber;        // 전화번호

    private String birthdate;          // 생년월일

    private String gender;             // 성별
}
