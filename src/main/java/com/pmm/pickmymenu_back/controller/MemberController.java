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

    // 회원가입 (회원정보만 폼데이터로 처리)
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestParam("memberInfo") String memberInfoJson) {
        try {
            // JSON 문자열을 MemberDTO 객체로 변환
            MemberDTO memberDTO = new ObjectMapper().readValue(memberInfoJson, MemberDTO.class);

            // 서비스에서 회원가입 처리
            memberService.joinProcess(memberDTO);

            return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("회원가입 실패: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody MemberDTO memberDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 로그인 처리 후 token과 email을 받아옴
            Map<String, String> loginResult = memberService.loginProcess(memberDTO);

            // 로그인 성공 후 token과 email을 추출하여 응답 객체에 추가
            String token = loginResult.get("token");
            String email = loginResult.get("email");

            response.put("token", token);
            response.put("email", email);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "로그인 실패: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // JWT 확인
    @PostMapping("/jwtChk")
    public ResponseEntity<Map<String, Object>> jwtChk(@RequestBody Map<String, String> tokens) {
        Map<String, Object> response = new HashMap<>();
        try {
            // JWT 토큰을 검증하고 이메일을 추출하여 반환
            String email = jwtUtil.validateAndExtract(tokens.get("token"));
            response.put("email", email);  // 이메일 반환

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "토큰이 유효하지 않거나 만료되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
