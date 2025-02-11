package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.service.AuthService;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    @Value("${data.kakao.key}")
    private String KAKAO_KEY;
    @Value("${data.kakao.redirect}")
    private String REDIRECT_URL;
    @Value("${data.kakao.secret}")
    private String SECRET;
    private final AuthService authService;

    @GetMapping("/kakao")
    public void goKakaoLoginPage(HttpServletResponse response, HttpSession session)throws IOException {
        String state = UUID.randomUUID().toString();
        session.setAttribute("oauth_state", state);
        response.sendRedirect("https://kauth.kakao.com/oauth/authorize?"
                + "client_id=" + KAKAO_KEY
                + "&redirect_uri="+ REDIRECT_URL
                +"&response_type=code&state=" + state);
    }
//    &response_type=code
//    https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=5442b5867fd4bcc644be6673084cf2b4&redirect_uri=http://localhost:8080/v1/auth/kakao/callback&response_type=code

    @GetMapping("/kakao/callback")
    public ResponseEntity kakaoCallback(
            @RequestParam("code") String code,
            @RequestParam("state") String state, HttpSession session
    ) {
        authService.checkSessionState(state, session);
        String token = authService.getKakaoAccessToken(code, KAKAO_KEY, SECRET, REDIRECT_URL);

        String frontendUrl = "http://localhost:3000/kakao/callback?token=" + token;
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", frontendUrl).build();

    }
}
