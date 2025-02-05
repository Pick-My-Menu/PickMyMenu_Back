package com.pmm.pickmymenu_back.dto.response.admin;

import com.pmm.pickmymenu_back.domain.Choice;
import com.pmm.pickmymenu_back.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChoicesRes {


    private Long id;
    private String question0;
    private String question1;
    private Long useCount;


    public ChoicesRes(Choice choice) {
        this.id = choice.getId();
        this.question0 = choice.getQuestion0();
        this.question1 = choice.getQuestion1();
        this.useCount = choice.getUseCount();
    }

}
