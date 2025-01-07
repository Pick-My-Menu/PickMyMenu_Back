package com.pmm.pickmymenu_back.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class VSGame {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vs1;
    private String vs2;

    @Column(name = "vs_group")
    private int vsGroup;
    private String relation;

}
