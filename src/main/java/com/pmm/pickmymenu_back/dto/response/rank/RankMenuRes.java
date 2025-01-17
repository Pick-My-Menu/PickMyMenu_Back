package com.pmm.pickmymenu_back.dto.response.rank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankMenuRes {

    private String menu;
    private Long menuCount;

}
