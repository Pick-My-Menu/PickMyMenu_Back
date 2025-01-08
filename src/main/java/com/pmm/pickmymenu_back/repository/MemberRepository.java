package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일 중복 확인
    Optional<Member> findByEmail(String email);
    
    // 전화번호 중복확인
    Optional<Member> findByPhoneNumber(String phoneNumber);

    // 회원번호로 회원 정보 조회
    Optional<Member> findById(Long memberId);

    // 로그인 시 이메일과 비밀번호로 회원 찾기
    Optional<Member> findByEmailAndPassword(String email, String password);  // loginId -> email로 변경
}

