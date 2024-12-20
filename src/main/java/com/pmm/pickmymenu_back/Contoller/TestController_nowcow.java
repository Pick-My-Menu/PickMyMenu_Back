package com.pmm.pickmymenu_back.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController_nowcow {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello!";
    }
}
