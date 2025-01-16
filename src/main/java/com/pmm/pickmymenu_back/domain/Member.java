package com.pmm.pickmymenu_back.domain;

import com.pmm.pickmymenu_back.dto.request.member.MemberJoinReq;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private String birthdate;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String gender;

    // ResultMenu와의 일대다 관계 설정
    @OneToMany(mappedBy = "member")
    private List<ResultMenu> resultMenuList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<SurveyGroup> surveyGroupList = new ArrayList<>();

    private Member(String name, String password, String email,
            String birthdate, String phoneNumber, String gender) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public static Member create(MemberJoinReq member) {
        return new Member(
                member.getName(),
                member.getPassword(),
                member.getEmail(),
                member.getBirthdate(),
                member.getPhoneNumber(),
                member.getGender()
        );
    }

    // 회원 정보 업데이트 메서드
    public void updateMember(String name, String phoneNumber, String password) {
        // name은 수정하지 않음
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }
    }
}