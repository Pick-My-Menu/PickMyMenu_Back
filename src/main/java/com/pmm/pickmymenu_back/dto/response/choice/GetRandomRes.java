package com.pmm.pickmymenu_back.dto.response.choice;

import com.pmm.pickmymenu_back.domain.Choice;
import lombok.Data;

@Data
public class GetRandomRes {
    private Long id;
    private String question0;
    private String question1;

    public GetRandomRes(Choice choice) {
        this.id = choice.getId();
        this.question0 = choice.getQuestion0();
        this.question1 = choice.getQuestion1();
    }
}
