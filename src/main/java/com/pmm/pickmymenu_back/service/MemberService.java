package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.MemberDTO;
import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.dto.request.member.MemberJoinReq;
import com.pmm.pickmymenu_back.dto.response.member.MemberEmailCheckRes;
import com.pmm.pickmymenu_back.exception.MemberException;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;


    // 회원가입 로직
    public void joinProcess(MemberJoinReq req) {

        boolean isEmailExist = memberRepository.findByEmail(req.getEmail()).isPresent();
        if (isEmailExist) throw new IllegalArgumentException("이미 등록된 이메일입니다.");

        boolean isPhoneNumberExist = memberRepository.findByPhoneNumber(req.getPhoneNumber()).isPresent();
        if (isPhoneNumberExist) throw new IllegalArgumentException("이미 등록된 전화번호입니다.");

        req.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
        Member member = Member.create(req);
        memberRepository.save(member);
    }

    // 로그인 로직
    public Map<String, String> loginProcess(MemberDTO memberDTO) {
        String email = memberDTO.getEmail();
        String password = memberDTO.getPassword();

        // 이메일 존재 여부 확인
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        // 비밀번호 일치 여부 확인
        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 로그인 성공 시 JWT 발급
        String token = jwtUtil.generateToken(email, member.getName());

        // 로그인 성공 후 token과 name을 함께 반환
        return Map.of(
                "token", token,
                "name", member.getName()
        );
    }

    // 마이페이지 조회
    public MemberDTO getMemberInfo(String email) {
        // 이메일로 멤버 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // Member 엔티티를 MemberDTO로 변환
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(member.getEmail());
        memberDTO.setName(member.getName());
        memberDTO.setPhoneNumber(member.getPhoneNumber());
        memberDTO.setBirthdate(member.getBirthdate());
        memberDTO.setGender(member.getGender());

        // 가입 날짜를 yyyy-MM-dd 형식으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        memberDTO.setCreatedDate(member.getCreatedDate().format(formatter));

        return memberDTO;
    }


    // 이메일 중복 확인 메서드
    public MemberEmailCheckRes isEmailExist(String email) {
        String normalizedEmail = email.trim().toLowerCase(); // 이메일 정규화
        Optional<Member> existEmail = memberRepository.findByEmail(normalizedEmail);
        if (existEmail.isPresent()) {
            throw new MemberException("이미 등록된 이메일입니다.");
        }
        return new MemberEmailCheckRes("사용 가능한 이메일입니다.");
    }

}
