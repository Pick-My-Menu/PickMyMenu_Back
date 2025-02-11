package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.domain.SurveyGroup;
import com.pmm.pickmymenu_back.dto.KakaoLoginDto;
import com.pmm.pickmymenu_back.dto.request.member.MemberAdminUpdateReq;
import com.pmm.pickmymenu_back.dto.request.member.MemberJoinReq;
import com.pmm.pickmymenu_back.dto.request.member.MemberLoginReq;
import com.pmm.pickmymenu_back.dto.request.member.MemberRecordReq;
import com.pmm.pickmymenu_back.dto.request.member.MemberUpdateReq;
import com.pmm.pickmymenu_back.dto.response.GetKakaoLoginDto;
import com.pmm.pickmymenu_back.dto.response.member.MemberEmailCheckRes;
import com.pmm.pickmymenu_back.dto.response.member.MemberLoginRes;
import com.pmm.pickmymenu_back.dto.response.member.MemberMyPageRes;
import com.pmm.pickmymenu_back.dto.response.member.MemberPhoneCheckRes;
import com.pmm.pickmymenu_back.dto.response.member.MemberRecordRes;
import com.pmm.pickmymenu_back.dto.response.member.MemberRecordRes.RecordSurveyGroupRes;
import com.pmm.pickmymenu_back.exception.MemberException;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.repository.SurveyGroupRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;
    private final SurveyGroupRepository surveyGroupRepository;


    public String createKakaoAccount(GetKakaoLoginDto userInfo, KakaoLoginDto response) {
        String email = userInfo.getId() + "@kakao.com";
        Optional<Member> existMember = memberRepository.findByEmail(email);
        Member member = null;
        String password = bCryptPasswordEncoder.encode(response.getAccess_token());
        if (existMember.isPresent()) {
            member = existMember.get();
            member.setPassword(password);
        }else{
            member = Member.createKakao(userInfo, password);
        }
        memberRepository.save(member);

        return jwtUtil.generateToken(member.getEmail(), member.getName(), member.getRole());
    }

    public String joinProcess(MemberJoinReq req) {

        boolean isEmailExist = memberRepository.findByEmail(req.getEmail()).isPresent();
        if (isEmailExist) throw new IllegalArgumentException("이미 등록된 이메일입니다.");

        boolean isPhoneNumberExist = memberRepository.findByPhoneNumber(req.getPhoneNumber()).isPresent();
        if (isPhoneNumberExist) throw new IllegalArgumentException("이미 등록된 전화번호입니다.");

        req.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));

        if (req.getRole() == null || req.getRole().isBlank()) {
            req.setRole("ROLE_USER");
        }

        Member member = Member.create(req);
        memberRepository.save(member);
        return "회원가입 성공";
    }
    // 로그인 로직

    public MemberLoginRes loginProcess(MemberLoginReq req, HttpServletResponse res) {

        Member member = memberRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!bCryptPasswordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtUtil.generateToken(member.getEmail(), member.getName(), member.getRole());
        res.addHeader("Set-Cookie", String.format("token=%s; HttpOnly; Path=/", token));
        System.out.println("token : " + token);
        return new MemberLoginRes(token, member.getName(), member.getRole());
    }
    // 마이페이지 조회

    public MemberMyPageRes getMemberInfo(String token) {

        if (token == null) throw new MemberException("토큰이 존재하지 않습니다.");

        String email = jwtUtil.validateAndExtract(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("해당 사용자를 찾을 수 없습니다."));

        return new MemberMyPageRes(member);
    }
    // 수정페이지 가기 전 비밀번호 확인

    public boolean verifyPassword(String token, String password) {

        if (token == null) throw new MemberException("토큰이 존재하지 않습니다.");

        String email = jwtUtil.validateAndExtract(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("해당 사용자를 찾을 수 없습니다."));

        return bCryptPasswordEncoder.matches(password, member.getPassword());
    }
    // 이메일 중복 확인

    public MemberEmailCheckRes isEmailExist(String email) {

        String normalizedEmail = email.trim().toLowerCase();
        Optional<Member> existEmail = memberRepository.findByEmail(normalizedEmail);
        if (existEmail.isPresent()) {
            throw new MemberException("이미 등록된 이메일입니다.");
        }

        return new MemberEmailCheckRes("사용 가능한 이메일입니다.");
    }
    // 전화번호 중복 확인

    public MemberPhoneCheckRes isPhoneExist(String phoneNumber) {

        Optional<Member> existPhone = memberRepository.findByPhoneNumber(phoneNumber);
        if (existPhone.isPresent()) {
            throw new MemberException("이미 등록된 전화번호입니다.");
        }

        return new MemberPhoneCheckRes("사용 가능한 전화번호입니다.");
    }
    // 회원 정보 업데이트 로직 (전화번호 변경 시에만 중복 체크)

    public boolean updateMember(MemberUpdateReq memberUpdateReq, String token) {

        if (token == null) throw new MemberException("토큰이 존재하지 않습니다.");

        String email = jwtUtil.validateAndExtract(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("해당 사용자를 찾을 수 없습니다."));

        if (!member.getPhoneNumber().equals(memberUpdateReq.getPhoneNumber())) {
            boolean isPhoneNumberExist = memberRepository.findByPhoneNumber(memberUpdateReq.getPhoneNumber()).isPresent();
            if (isPhoneNumberExist) {
                throw new MemberException("이미 등록된 전화번호입니다.");
            }
        }

        member.updateMember(
                member.getName(),
                memberUpdateReq.getPhoneNumber(),
                (memberUpdateReq.getPassword() == null || memberUpdateReq.getPassword().isEmpty()) ?
                        member.getPassword() : bCryptPasswordEncoder.encode(memberUpdateReq.getPassword())
        );

        memberRepository.save(member);
        return true;
    }
    // 회원탈퇴

    public void deleteMember(String token) {
        if (token == null) throw new MemberException("토큰이 존재하지 않습니다.");

        String email = jwtUtil.validateAndExtract(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("해당 사용자를 찾을 수 없습니다."));

        memberRepository.delete(member);
    }

    public MemberRecordRes memberSurveyRecord(MemberRecordReq req, String token) {
        String email = jwtUtil.validateAndExtract(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("해당 이메일이 존재하지 않습니다."));
        List<SurveyGroup> groupByMemberAndGetMenu = surveyGroupRepository.findGroupByMemberAndGetMenu(
                member);
        List<RecordSurveyGroupRes> list = groupByMemberAndGetMenu.stream()
                .map(RecordSurveyGroupRes::new).toList();

        return new MemberRecordRes(list);
    }

    public String adminUpdate(Long id, MemberAdminUpdateReq req, String token) {
        adminCheck(token);

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException("해당 ID 가 존재하지 않습니다."));
        member.update(req);
        memberRepository.save(member);
        return "변경 성공.";
    }

    public void adminCheck(String token) {
        String email = jwtUtil.validateAndExtract(token);
        Member admin = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("관리자가 아닙니다."));
        if (!admin.getRole().equals("ROLE_ADMIN")) {
            throw new MemberException("관리자가 아닙니다.");
        }
    }
}
