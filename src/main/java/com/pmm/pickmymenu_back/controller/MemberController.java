package com.pmm.pickmymenu_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.dto.MemberDTO;
import com.pmm.pickmymenu_back.dto.request.member.MemberJoinReq;
import com.pmm.pickmymenu_back.dto.response.member.MemberEmailCheckRes;
import com.pmm.pickmymenu_back.exception.MemberException;
import com.pmm.pickmymenu_back.service.MemberService;
import com.pmm.pickmymenu_back.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JWTUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입
    @PostMapping("/join")
    public BaseResponse<String> join(@RequestBody MemberJoinReq req) {
        memberService.joinProcess(req);
        return BaseResponse.success("회원가입 성공");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody MemberDTO memberDTO) {
        try {
            // 로그인 처리 후 JWT 생성
            Map<String, String> loginResult = memberService.loginProcess(memberDTO);

            // 로그인 성공 시 token 값을 추출
            String token = loginResult.get("token");
            String name = loginResult.get("name");

            // 쿠키 설정
            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
            responseBuilder.header("Set-Cookie",
                    String.format("token=%s; HttpOnly; Path=/", token));

            // name과 token을 포함한 JSON 반환
            Map<String, String> response = new HashMap<>();
            response.put("name", name); // 이름을 추가
            response.put("token", token); // 토큰도 포함

            return responseBuilder.body(response);
        } catch (IllegalArgumentException e) {
            // 로그인 실패 시 적절한 메시지 반환
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "로그인 처리 중 오류가 발생했습니다.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 마이페이지 정보 조회
    @GetMapping("/mypage")
    public ResponseEntity<MemberDTO> getMyPage(@CookieValue(value = "token", required = false) String token) {
        try {
            if (token == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 토큰 없음
            }

            // JWT에서 이메일 추출
            String email = jwtUtil.validateAndExtract(token);

            // 서비스에서 사용자 정보 가져오기
            MemberDTO memberDTO = memberService.getMemberInfo(email);

            return ResponseEntity.ok(memberDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 인증 실패
        }
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
