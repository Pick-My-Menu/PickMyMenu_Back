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

                // 서비스에서 회원가입 처리
                memberService.joinProcess(memberDTO);

                return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("회원가입 실패: " + e.getMessage(), HttpStatus.BAD_REQUEST);
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
                // Secure; SameSite=Strict; http://localhost에서는 Secure 쿠키가 적용되지 않음

                // name과 token을 포함한 JSON 반환
                Map<String, String> response = new HashMap<>();
                response.put("name", name); // 이름을 추가
                response.put("token", token); // 토큰도 포함

                return responseBuilder.body(response);
            } catch (Exception e) {
                return new ResponseEntity<>(Map.of("message", "로그인 실패: " + e.getMessage()), HttpStatus.UNAUTHORIZED);
            }
        }


        // JWT 확인
        @PostMapping("/jwtChk")
        public ResponseEntity<String> jwtChk(@CookieValue(value = "token", required = false) String token) {
            try {
                if (token == null) {
                    throw new IllegalArgumentException("토큰이 없습니다.");
                }

                // JWT 검증
                String email = jwtUtil.validateAndExtract(token);
                return ResponseEntity.ok("인증 성공: " + email);
            } catch (Exception e) {
                return new ResponseEntity<>("인증 실패: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
            }
        }
    }
