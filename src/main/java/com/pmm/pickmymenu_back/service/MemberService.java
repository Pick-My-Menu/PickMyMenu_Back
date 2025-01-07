package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.MemberDTO;
import com.pmm.pickmymenu_back.domain.MemberEntity;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(MemberDTO memberDTO) {

        String username = memberDTO.getUsername();
        String password = memberDTO.getPassword();
        String email = memberDTO.getEmail();
        String birthdate = memberDTO.getBirthdate();
        String phoneNumber = memberDTO.getPhoneNumber();
        String gender = memberDTO.getGender();

//        // Username 중복 확인 ( 단순 종료 방식 )
//        Boolean isExist = userRepository.existsByUsername(username);
//
//        if (isExist) {
//
//            return;
//        }

        // Username 중복 확인 ( 사용자 알림 방식 )
        boolean isUsernameExist = memberRepository.existsByUsername(username);
        if (isUsernameExist) {
            throw new IllegalArgumentException("이미 사용 중인 사용자명입니다.");
        }

        // email 중복 확인
        boolean isEmailExist = memberRepository.findByEmail(email).isPresent();
        if (isEmailExist) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 새로운 사용자 생성
        MemberEntity data = new MemberEntity();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));// 암호화된 비밀번호
        data.setEmail(email);
        data.setBirthdate(birthdate);
        data.setPhoneNumber(phoneNumber);
        data.setGender(gender);
        data.setRole("ROLE_ADMIN"); // 기본 역할 설정

        memberRepository.save(data);
    }
}
