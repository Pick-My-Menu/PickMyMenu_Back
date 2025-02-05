package com.pmm.pickmymenu_back.config;

import com.pmm.pickmymenu_back.domain.Member;
import com.pmm.pickmymenu_back.dto.request.member.MemberJoinReq;
import com.pmm.pickmymenu_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminConfig implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private  final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@admin.com";
        String adminPhone = "000-0000-0000";

        boolean isAdminExist = memberRepository.findByEmail(adminEmail).isPresent();
        if (!isAdminExist) {
            MemberJoinReq admin = new MemberJoinReq(
                    adminEmail,
                    passwordEncoder.encode("admin"),
                    "관리자",
                    "0000-00-00",
                    adminPhone,
                    "남",
                    "ROLE_ADMIN"
            );

            Member adminCreate = Member.create(admin);
            memberRepository.save(adminCreate);

            System.out.println("관리자 등록");
        }

    }
}
