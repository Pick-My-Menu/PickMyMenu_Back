package com.pmm.pickmymenu_back.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PageRes <T extends Page> {

    private T page;
    private Integer totalPages;
    private Long totalElements;

    public PageRes(T page) {
        this.page = page;
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
