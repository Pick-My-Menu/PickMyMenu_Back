package com.pmm.pickmymenu_back.dto.response.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class PlaceSearchRes {

    private Meta meta;
    private List<Object> documents;

    @Data
    public static class Meta {

        private int total_count;
        private int pageable_count;
        private Boolean is_end;
        private Object same_name;
    }

    public PlaceSearchRes(Meta meta, List<Object> documents) {
        this.meta = meta;
        this.documents = documents;
    }
}