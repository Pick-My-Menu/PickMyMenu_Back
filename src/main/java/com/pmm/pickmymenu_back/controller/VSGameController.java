package com.pmm.pickmymenu_back.controller;

import com.pmm.pickmymenu_back.domain.VSGame;
import com.pmm.pickmymenu_back.service.VSGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vs")
public class VSGameController {

    private  final VSGameService vsGameService;

    @GetMapping("/group1")
    public ResponseEntity<Map<String, String>> getRandomVSGroup1() {
        VSGame vsGame = vsGameService.getRandomVSGroup1();

        if (vsGame != null) {
            Map<String, String> response = new HashMap<>();
            response.put("vs1", vsGame.getVs1());
            response.put("vs2", vsGame.getVs2());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/vs/related/{selected}")
    public VSGame getRelatedVSGroup2(@PathVariable String selected) {
        return vsGameService.getRelatedVSGroup2(selected);
    }


}
