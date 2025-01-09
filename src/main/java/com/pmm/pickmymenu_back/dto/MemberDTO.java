package com.pmm.pickmymenu_back.dto;

import lombok.Data;

@Data
public class MemberDTO {

    private Long id;          // 회원 번호 (고유 식별자)

    private String name;               // 사용자 이름

    private String password;           // 비밀번호 (암호화된 비밀번호)

    private String token1;             // 첫 번째 토큰 (예: 사용자 이메일 토큰)

    private String token2;             // 두 번째 토큰 (예: 사용자 이름 토큰)

    private String sessionInfo;        // 세션 정보

    private String sessionLogin;       // 로그인 세션 상태

    private String email;              // 이메일 (로그인 및 회원가입 시 사용)

    private String phoneNumber;        // 전화번호

    private String birthdate;          // 생년월일

    private String gender;             // 성별
}
