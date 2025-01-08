package com.pmm.pickmymenu_back.domain;

import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Member extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;  // 로그인 아이디 (회원가입 시 사용될 아이디)

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

//    @Column
//    private String role; // 스프링 시큐리티와 함께 사용하면 Admin에게만 권한 부여

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String birthdate;

    @Column
    private String phoneNumber;

    @Column
    private String gender;

    // ResultMenu와의 일대다 관계 설정
    @OneToMany(mappedBy = "member")
    private List<ResultMenu> memberList;
}

