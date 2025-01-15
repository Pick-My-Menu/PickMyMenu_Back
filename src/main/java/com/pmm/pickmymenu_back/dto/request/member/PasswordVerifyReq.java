package com.pmm.pickmymenu_back.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordVerifyReq {
    private String password; // 비밀번호만 받아옴
}
