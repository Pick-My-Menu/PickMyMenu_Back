package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.MemberDTO;
import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        this.jwtUtil = jwtUtil;  // JWTUtil을 생성자를 통해 주입받기
    }

    // 회원가입 로직
    public void joinProcess(MemberDTO memberDTO) {
        String loginId = memberDTO.getLoginId();
        String name =  memberDTO.getName(); // 01.08
        String password = memberDTO.getPassword();
        String email = memberDTO.getEmail();
        String birthdate = memberDTO.getBirthdate();
        String phoneNumber = memberDTO.getPhoneNumber();
        String gender = memberDTO.getGender();

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
        member.setName(name); // 01.08
        member.setLoginId(loginId);
        member.setPassword(bCryptPasswordEncoder.encode(password));  // 비밀번호 암호화
        member.setEmail(email);
        member.setBirthdate(birthdate);
        member.setPhoneNumber(phoneNumber);
        member.setGender(gender);
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
        return jwtUtil.generateToken(loginId);  // 비정적 메소드 호출
    }
}
