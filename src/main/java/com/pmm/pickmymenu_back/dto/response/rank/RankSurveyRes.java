package com.pmm.pickmymenu_back.dto.response.rank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RankSurveyRes {
    private Long choiceId;
    private String question0;
    private Long question0Count;
    private String question1;
    private Long question1Count;
    private Long totalCount;
}
