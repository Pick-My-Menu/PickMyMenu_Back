package com.pmm.pickmymenu_back.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class GetKakaoLoginDto {

    private Long id;
    private KakaoAccount kakao_account;
    

    @Data
    @NoArgsConstructor
    @ToString
    public static class KakaoAccount{
        private Boolean profile_nickname_needs_agreement;
        private String email;
        private String birthday;
        private String gender;
        private Profile profile;
    }

    @Data
    @NoArgsConstructor
    @ToString
    public static class Profile{
        private String nickname;
        private Boolean is_default_nickname;
    }

}
