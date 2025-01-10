package com.pmm.pickmymenu_back.dto.request.choice;

import java.util.List;
import lombok.Data;

@Data
public class GetRandomReq {
    private List<Long> choiceIdList;


}
