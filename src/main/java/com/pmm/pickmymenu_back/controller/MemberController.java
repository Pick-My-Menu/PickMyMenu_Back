package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.request.member.*;
import com.pmm.pickmymenu_back.dto.response.member.*;
import com.pmm.pickmymenu_back.service.MemberService;
import com.pmm.pickmymenu_back.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    // 회원가입
    @PostMapping("/join")
    public BaseResponse<String> join(@RequestBody MemberJoinReq req) {
        String result = memberService.joinProcess(req);
        return BaseResponse.success(result);
    }

    // 로그인
    @PostMapping("/login")
    public BaseResponse<MemberLoginRes> login(@RequestBody MemberLoginReq req, HttpServletResponse res) {
        MemberLoginRes result = memberService.loginProcess(req, res);
        return BaseResponse.success(result);
    }

    // 수정 페이지 가기 전 비밀번호 확인
    @PostMapping("/verify-password")
    public BaseResponse<Boolean> verifyPassword(
            @CookieValue(value = "token", required = false) String token,
            @RequestBody PasswordVerifyReq req) {
        boolean result = memberService.verifyPassword(token, req.getPassword());
        return result ? BaseResponse.success(true) : BaseResponse.fail("비밀번호가 일치하지 않습니다.");
    }

    // 마이페이지 정보 조회
    @GetMapping("/mypage")
    public BaseResponse<MemberMyPageRes> getMyPage(@CookieValue(value = "token", required = false) String token) {
        MemberMyPageRes result = memberService.getMemberInfo(token);
        return BaseResponse.success(result);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok()
                .header("Set-Cookie", "token=; Max-Age=0; Path=/; HttpOnly")
                .body("로그아웃 성공");
    }

    // 이메일 중복 확인
    @GetMapping("/check-email")
    public BaseResponse<MemberEmailCheckRes> checkEmail(@RequestParam String email) {
        MemberEmailCheckRes result = memberService.isEmailExist(email);
        return BaseResponse.success(result);
    }

    // 회원정보 수정
    @PutMapping("/update")
    public BaseResponse<String> updateMember(@RequestBody MemberUpdateReq memberUpdateReq,
                                             @CookieValue(value = "token", required = false) String token) {
        boolean isUpdated = memberService.updateMember(memberUpdateReq, token);
        return isUpdated ? BaseResponse.success("회원 정보가 성공적으로 업데이트 되었습니다.")
                : BaseResponse.fail("회원 정보 업데이트에 실패했습니다.");
    }

    // 수정 페이지에서 전화번호 확인
    @GetMapping("/check-phone")
    public BaseResponse<MemberPhoneCheckRes> checkPhoneNumber(@RequestParam String phoneNumber) {
        MemberPhoneCheckRes result = memberService.isPhoneExist(phoneNumber);
        return BaseResponse.success(result);
    }

    // JWT 인증
    @PostMapping("/jwtChk")
    public ResponseEntity<String> jwtChk(@CookieValue(value = "token", required = false) String token) {
        try {
            if (token == null) {
                throw new IllegalArgumentException("토큰이 없습니다.");
            }

            String email = jwtUtil.validateAndExtract(token);

            if (jwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .header("Set-Cookie", "token=; Max-Age=0; Path=/; HttpOnly")
                        .body("토큰이 만료되어 로그아웃 처리되었습니다.");
            }

            return ResponseEntity.ok("인증 성공: " + email);

        } catch (Exception e) {
            return new ResponseEntity<>("인증 실패: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
