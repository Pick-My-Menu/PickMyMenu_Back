package com.pmm.pickmymenu_back.dto.response.map;

import java.util.List;
import lombok.Data;

@Data
public class PlaceSearchRes {

    private Meta meta;
    private List<Document> documents;

    @Data
    public static class Meta {

        private int total_count;
        private int pageable_count;
        private Boolean is_end;
        private Object same_name;
    }

    @Data
    public static class Document {

        private String address_name;
        private String category_group_code;
        private String category_group_name;
        private String category_name;
        private Integer distance;
        private Long id;
        private String phone;
        private String place_name;
        private String place_url;
        private String road_address_name;
        private Double x;
        private Double y;
        private String image_url;

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }

    public PlaceSearchRes(Meta meta, List<Document> documents) {
        this.meta = meta;
        this.documents = documents;
    }
}