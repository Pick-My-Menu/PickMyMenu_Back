package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.dto.MemberDTO;
import com.pmm.pickmymenu_back.domain.MemberEntity;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import com.pmm.pickmymenu_back.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Autowired
    public MemberController(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberDTO memberDTO) {
        try {
            String username = memberDTO.getUsername();
            String password = memberDTO.getPassword();
            String email = memberDTO.getEmail();
            String birthdate = memberDTO.getBirthdate();
            String phoneNumber = memberDTO.getPhoneNumber();
            String gender = memberDTO.getGender();

            // Username 중복 확인
            boolean isUsernameExist = memberRepository.existsByUsername(username);
            if (isUsernameExist) {
                throw new IllegalArgumentException("이미 사용 중인 사용자명입니다.");
            }

            // Email 중복 확인
            boolean isEmailExist = memberRepository.findByEmail(email).isPresent();
            if (isEmailExist) {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            }

            // 새로운 사용자 생성
            MemberEntity data = new MemberEntity();
            data.setUsername(username);
            data.setPassword(passwordEncoder.encode(password)); // 암호화된 비밀번호
            data.setEmail(email);
            data.setBirthdate(birthdate);
            data.setPhoneNumber(phoneNumber);
            data.setGender(gender);
            data.setRole("ROLE_USER"); // 기본 역할 설정

            memberRepository.save(data); // DB에 사용자 정보 저장

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
            Optional<MemberEntity> user = memberRepository.findByUsername(memberDTO.getUsername());

            if (user.isPresent() && passwordEncoder.matches(memberDTO.getPassword(), user.get().getPassword())) {
                // 로그인 성공 시 JWT 발급
                String token = jwtUtil.generateToken(memberDTO.getUsername());
                response.put("token", token);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "아이디 또는 비밀번호가 잘못되었습니다.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
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
            String username = jwtUtil.validateAndExtract(tokens.get("token"));
            response.put("username", username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "토큰이 유효하지 않거나 만료되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
