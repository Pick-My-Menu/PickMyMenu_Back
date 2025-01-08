package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 로그인 아이디 존재 여부 확인
    Boolean existsByLoginId(String loginId);  // name -> loginId로 변경

    // 이메일 중복 확인
    Optional<Member> findByEmail(String email);

    // 회원가입 시 아이디와 이메일 중복 체크
    Optional<Member> findByLoginIdAndEmail(String loginId, String email);  // name -> loginId로 변경

    // 로그인 시 아이디와 비밀번호로 찾기 (로그인 시 사용할 수 있음)
    Optional<Member> findByLoginIdAndPassword(String loginId, String password);  // name -> loginId로 변경

    // 회원번호로 회원 정보 조회
    Optional<Member> findById(Long memberId);

    // 로그인 아이디로 회원 찾기
    Optional<Member> findByLoginId(String loginId); // 이 메서드를 추가
}
