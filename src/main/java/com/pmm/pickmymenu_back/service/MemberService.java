package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.MemberDTO;
import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;

    @Autowired
    public MemberService(MemberRepository memberRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder,
                         JWTUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입 로직
    public void joinProcess(MemberDTO memberDTO) {

        // 이메일과 전화번호 중복 확인
        boolean isEmailExist = memberRepository.findByEmail(memberDTO.getEmail()).isPresent();
        boolean isPhoneNumberExist = memberRepository.findByPhoneNumber(memberDTO.getPhoneNumber()).isPresent();

        // 이메일 중복 확인
        if (isEmailExist) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        // 전화번호 중복 확인
        if (isPhoneNumberExist) {
            throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
        }

        // 새로운 사용자 생성
        Member member = new Member();
        member.setEmail(memberDTO.getEmail());
        member.setName(memberDTO.getName());
        member.setPassword(bCryptPasswordEncoder.encode(memberDTO.getPassword())); // 비밀번호 암호화
        member.setBirthdate(memberDTO.getBirthdate());
        member.setPhoneNumber(memberDTO.getPhoneNumber());
        member.setGender(memberDTO.getGender());

        memberRepository.save(member);
    }

    // 로그인 로직
    public Map<String, String> loginProcess(MemberDTO memberDTO) {
        String email = memberDTO.getEmail();
        String password = memberDTO.getPassword();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        // 로그인 성공 시 JWT 발급
        String token = jwtUtil.generateToken(email, member.getName());

        // 로그인 성공 후 token과 name을 함께 반환
        return Map.of(
                "token", token,
                "name", member.getName() // 01.10
        );
    }
}
