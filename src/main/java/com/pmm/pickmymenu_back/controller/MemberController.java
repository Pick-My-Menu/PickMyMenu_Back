package com.pmm.pickmymenu_back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmm.pickmymenu_back.dto.MemberDTO;
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
    public ResponseEntity<String> join(@RequestParam("memberInfo") String memberInfoJson) {
        try {
            // JSON 문자열을 MemberDTO 객체로 변환
            MemberDTO memberDTO = new ObjectMapper().readValue(memberInfoJson, MemberDTO.class);

            if ("male".equals(memberDTO.getGender())) {
                memberDTO.setGender("남");
            } else if ("female".equals(memberDTO.getGender())) {
                memberDTO.setGender("여");
            } else {
                // 유효하지 않은 성별 값 처리
                return new ResponseEntity<>("성별 값이 잘못되었습니다.", HttpStatus.BAD_REQUEST);
            }

            // 서비스에서 회원가입 처리
            memberService.joinProcess(memberDTO);

            return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
            responseBuilder.header("Set-Cookie", String.format("token=%s; HttpOnly; Path=/", token));

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

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 쿠키를 만료시키기 위한 헤더 설정
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
        responseBuilder.header("Set-Cookie", "token=; Max-Age=0; Path=/; HttpOnly");

        return responseBuilder.body("로그아웃 성공");
    }

    // 이메일 중복 확인
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, String>> checkEmail(@RequestParam String email) {
        try {
            boolean isEmailExist = memberService.isEmailExist(email);
            Map<String, String> response = new HashMap<>();
            if (isEmailExist) {
                response.put("message", "이미 등록된 이메일입니다.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 이메일 중복 시 BAD_REQUEST
            } else {
                response.put("message", "사용 가능한 이메일입니다.");
                return new ResponseEntity<>(response, HttpStatus.OK); // 사용 가능 시 OK
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "이메일 확인 중 오류가 발생했습니다: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/jwtChk")
    public ResponseEntity<String> jwtChk(@CookieValue(value = "token", required = false) String token) {
        try {
            if (token == null) {
                throw new IllegalArgumentException("토큰이 없습니다.");
            }

            // JWT 검증
            String email = jwtUtil.validateAndExtract(token);

            // JWT가 만료되었으면 쿠키를 삭제하고 응답
            if (jwtUtil.isTokenExpired(token)) {
                // ResponseEntity 방식으로 쿠키 삭제
                ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(HttpStatus.UNAUTHORIZED);
                responseBuilder.header("Set-Cookie", "token=; Max-Age=0; Path=/; HttpOnly");

                return responseBuilder.body("토큰이 만료되어 로그아웃 처리되었습니다.");
            }

            return ResponseEntity.ok("인증 성공: " + email);
        } catch (Exception e) {
            return new ResponseEntity<>("인증 실패: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
