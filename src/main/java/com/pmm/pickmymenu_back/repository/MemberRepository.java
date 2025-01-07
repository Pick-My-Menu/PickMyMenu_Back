package com.pmm.pickmymenu_back.repository;

import com.pmm.pickmymenu_back.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    // Username 존재 여부 확인
    Boolean existsByUsername(String username);

    // 이메일 중복 확인
    Optional<MemberEntity> findByEmail(String email);

    // 회원가입 시 아이디와 이메일 중복 체크
    Optional<MemberEntity> findByUsernameAndEmail(String username, String email);

    // 로그인 시 아이디와 비밀번호로 찾기 (예시: 로그인 시 사용할 수 있음)
    Optional<MemberEntity> findByUsernameAndPassword(String username, String password);

    // 회원번호로 회원 정보 조회
    Optional<MemberEntity> findById(Integer memberId);

    Optional<MemberEntity> findByUsername(String username);
}
