package com.pmm.pickmymenu_back.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateReq {

    private String password;
    private String phoneNumber;
}
