package com.pmm.pickmymenu_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

// global exception handler 사용하기위해 기본 에러핸들러 비활성화
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class PickMyMenuBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickMyMenuBackApplication.class, args);
    }

}
