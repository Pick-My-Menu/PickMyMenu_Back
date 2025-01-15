package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.MemberDTO;
import com.pmm.pickmymenu_back.dto.request.member.MemberJoinReq;
import com.pmm.pickmymenu_back.dto.request.member.MemberLoginReq;
import com.pmm.pickmymenu_back.dto.request.member.PasswordVerifyReq;
import com.pmm.pickmymenu_back.dto.response.member.MemberEmailCheckRes;
import com.pmm.pickmymenu_back.dto.response.member.MemberLoginRes;
import com.pmm.pickmymenu_back.dto.response.member.MemberMyPageRes;
import com.pmm.pickmymenu_back.service.MemberService;
import com.pmm.pickmymenu_back.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    
    // 수정페이지 가기 전 비밀번호 확인
    @PostMapping("/verify-password")
    public BaseResponse<Boolean> verifyPassword(
            @CookieValue(value = "token", required = false) String token,
            @RequestBody PasswordVerifyReq req) {
        boolean result = memberService.verifyPassword(token, req.getPassword());
        if (result) {
            return BaseResponse.success(true);
        } else {
            return BaseResponse.fail("비밀번호가 일치하지 않습니다.");
        }
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
        // 쿠키를 만료시키기 위한 헤더 설정
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
        responseBuilder.header("Set-Cookie", "token=; Max-Age=0; Path=/; HttpOnly");

        return responseBuilder.body("로그아웃 성공");
    }

    // 이메일 중복 확인
    @GetMapping("/check-email")
    public BaseResponse<MemberEmailCheckRes> checkEmail(@RequestParam String email) {
        MemberEmailCheckRes result = memberService.isEmailExist(email);
        return BaseResponse.success(result);
    }

    // jwt
    @PostMapping("/jwtChk")
    public ResponseEntity<String> jwtChk(
            @CookieValue(value = "token", required = false) String token) {
        try {
            if (token == null) {
                throw new IllegalArgumentException("토큰이 없습니다.");
            }

            // JWT 검증
            String email = jwtUtil.validateAndExtract(token);

            // JWT가 만료되었으면 쿠키를 삭제하고 응답
            if (jwtUtil.isTokenExpired(token)) {
                // ResponseEntity 방식으로 쿠키 삭제
                ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED);
                responseBuilder.header("Set-Cookie", "token=; Max-Age=0; Path=/; HttpOnly");

                return responseBuilder.body("토큰이 만료되어 로그아웃 처리되었습니다.");
            }

            return ResponseEntity.ok("인증 성공: " + email);
        } catch (Exception e) {
            return new ResponseEntity<>("인증 실패: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}