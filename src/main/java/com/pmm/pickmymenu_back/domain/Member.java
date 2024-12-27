package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private MemberType type;
    @Column
    private String refreshToken;

//    @OneToMany(mappedBy = "member")
//    private List<RoomMember> roomMemberList = new ArrayList<>();

//    @Builder
//    public Member(Long id, String email, String password, String name, MemberType type, String refreshToken) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.type = type;
//        this.refreshToken = refreshToken;
//    }

//    public static Member createMember(SignupRequest req){
//        return Member.builder()
//            .email(req.getEmail())
//            .password(req.getPassword())
//            .name(req.getName())
//            .type(MemberType.USER)
//            .build();
//    }


}
