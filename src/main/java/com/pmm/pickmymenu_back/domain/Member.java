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
    private String email;   // 이메일로 회원가입 및 로그인

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

//    @Column
//    private String role; // 스프링 시큐리티와 함께 사용하면 Admin에게만 권한 부여

    @Column(nullable = false)
    private String birthdate;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String gender;

    // ResultMenu와의 일대다 관계 설정
    @OneToMany(mappedBy = "member")
    private List<ResultMenu> resultMenuList;

    @OneToMany(mappedBy = "member")
    private List<Survey> surveyList;

    private Member(String name, String password, String email,
            String birthdate, String phoneNumber, String gender) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public static Member create(String name, String password, String email,
            String birthdate, String phoneNumber, String gender) {
        return new Member(name, password, email, birthdate, phoneNumber, gender);
    }
}

