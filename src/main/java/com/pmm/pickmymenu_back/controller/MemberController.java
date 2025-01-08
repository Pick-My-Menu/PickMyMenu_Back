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
            String token = memberService.loginProcess(memberDTO);  // 서비스에서 로그인 처리
            response.put("token", token);
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
            String loginId = jwtUtil.validateAndExtract(tokens.get("token"));
            response.put("loginId", loginId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "토큰이 유효하지 않거나 만료되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
