package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.MemberDTO;
import com.pmm.pickmymenu_back.domain.Member;
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

    // 회원가입 로직
    public void joinProcess(MemberDTO memberDTO) {
        String loginId = memberDTO.getLoginId();
        String password = memberDTO.getPassword();
        String email = memberDTO.getEmail();
        String birthdate = memberDTO.getBirthdate();
        String phoneNumber = memberDTO.getPhoneNumber();
        String gender = memberDTO.getGender();
        String name = memberDTO.getName();

        // 로그인 아이디 중복 확인
        boolean isLoginIdExist = memberRepository.existsByLoginId(loginId);
        if (isLoginIdExist) {
            throw new IllegalArgumentException("이미 사용 중인 로그인 아이디입니다.");
        }

        // 이메일 중복 확인
        boolean isEmailExist = memberRepository.findByEmail(email).isPresent();
        if (isEmailExist) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 새로운 사용자 생성
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(bCryptPasswordEncoder.encode(password));  // 비밀번호 암호화
        member.setEmail(email);
        member.setBirthdate(birthdate);
        member.setPhoneNumber(phoneNumber);
        member.setGender(gender);
        member.setName(name);
//        member.setRole("ROLE_USER");  // 기본 역할 설정

        memberRepository.save(member);
    }

    // 로그인 로직
    public String loginProcess(MemberDTO memberDTO) {
        String loginId = memberDTO.getLoginId();
        String password = memberDTO.getPassword();

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다."));

        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        // 로그인 성공 시 JWT 발급
        return member.getLoginId();  // JWT를 발급하는 로직 추가 필요 (현재는 loginId 반환)
    }
}
