package com.pmm.pickmymenu_back.service;

import com.pmm.pickmymenu_back.dto.KakaoLoginDto;
import com.pmm.pickmymenu_back.dto.response.GetKakaoLoginDto;
import com.pmm.pickmymenu_back.util.JWTUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final WebClient kakaoToken;
    private final WebClient kakaoToken2;
    private final JWTUtil jwtUtil;

    private final MemberService memberService;



    public void checkSessionState(String state, HttpSession session) {
        String oauthState = (String) session.getAttribute("oauth_state");
        if (oauthState == null || !oauthState.equals(state)) {
            throw new RuntimeException("state 값이 맞지 않습니다. 의심스러운 요청입니다.");
        }

    }

    public String getKakaoAccessToken(String code, String KAKAO_KEY, String REDIRECT_URL) {

        KakaoLoginDto response = getKakaoKey(code, KAKAO_KEY, REDIRECT_URL);
        GetKakaoLoginDto userInfo = getKakaoUserInfo(response);
        return memberService.createKakaoAccount(userInfo, response);
    }

    private GetKakaoLoginDto getKakaoUserInfo(KakaoLoginDto response) {
        return kakaoToken2.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/user/me")
                        .queryParam("property_keys", "[\"kakao_account.name\",\"kakao_account.profile\"]")
                        .build())
                .header("Authorization", "Bearer " + response.getAccess_token())
                .header("Content-type", MediaType.APPLICATION_JSON_VALUE)  // application/json으로 수정
                .retrieve()
                .bodyToMono(GetKakaoLoginDto.class)
                .block();
    }

    private KakaoLoginDto getKakaoKey(String code, String KAKAO_KEY, String REDIRECT_URL) {
        return kakaoToken.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", KAKAO_KEY)
                        .queryParam("redirect_uri", REDIRECT_URL)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .bodyToMono(KakaoLoginDto.class)
                .block();
    }
}
