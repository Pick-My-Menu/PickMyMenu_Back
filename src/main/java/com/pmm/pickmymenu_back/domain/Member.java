package com.pmm.pickmymenu_back.domain;

import com.pmm.pickmymenu_back.dto.request.member.MemberAdminUpdateReq;
import com.pmm.pickmymenu_back.dto.request.member.MemberJoinReq;
import com.pmm.pickmymenu_back.dto.response.GetKakaoLoginDto;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import javax.swing.JPasswordField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = true)
    private String birthdate;

    @Column(nullable = true, unique = true)
    private String phoneNumber;

    @Column(nullable = true)
    private String gender;

    @Column(nullable = false)
    private String role;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    // ResultMenu와의 일대다 관계 설정
    @OneToMany(mappedBy = "member")
    private List<ResultMenu> resultMenuList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<SurveyGroup> surveyGroupList = new ArrayList<>();

    private Member(String name, String password, String email,
            String birthdate, String phoneNumber, String gender, String role, LoginType loginType) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
        this.loginType = loginType;
    }

    public static Member createKakao(GetKakaoLoginDto userInfo, String password) {
        return new Member(
                userInfo.getKakao_account().getProfile().getNickname(),
                password,
                userInfo.getKakao_account().getEmail(),
                userInfo.getKakao_account().getBirthday(),
                null,
                userInfo.getKakao_account().getGender().equals("male") ? "남" : "여",
                "ROLE_USER",
                LoginType.KAKAO
        );
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Member create(MemberJoinReq member) {
        return new Member(
                member.getName(),
                member.getPassword(),
                member.getEmail(),
                member.getBirthdate(),
                member.getPhoneNumber(),
                member.getGender(),
                member.getRole(),
                null
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

    public void update(MemberAdminUpdateReq req) {
        this.birthdate = req.getBirthdate();
        this.gender = req.getGender();
        this.name = req.getName();
        this.phoneNumber = req.getPhoneNumber();

    }
}