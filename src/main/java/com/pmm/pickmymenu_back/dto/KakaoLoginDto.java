package com.pmm.pickmymenu_back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KakaoLoginDto {

    private Long refresh_token_expires_in;
    private String scope;
    private Long expires_in;
    private String id_token;
    private String refresh_token;
    private String token_type;
    private String access_token;

}
