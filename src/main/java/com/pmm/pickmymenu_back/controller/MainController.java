package com.pmm.pickmymenu_back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// @RestController자체가 @Controller, @ResponseBody의 집합체이기 때문에 합쳐서 대신 사용 가능
@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String mainP() {

        return "Main Controller";
    }
}
