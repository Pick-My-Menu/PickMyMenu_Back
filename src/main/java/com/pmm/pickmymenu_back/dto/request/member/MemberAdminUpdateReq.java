package com.pmm.pickmymenu_back.dto.request.member;

import lombok.Data;

@Data
public class MemberAdminUpdateReq {

    private String birthdate;
    private String gender;
    private String name;
    private String phoneNumber;

}
