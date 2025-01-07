package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId; // 고유 식별자

    private String username;

    private String password;

    private String role; // 스프링 시큐리티와 함께 사용하면 Admin에게만 권한 부여

    private String email;

    private String birthdate;

    private String phoneNumber;

    private String gender;
}
