package com.pmm.pickmymenu_back.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse<T> {

    private Boolean success;
    private String message;
    private T data;

    public BaseResponse(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, "Success", data);
    }

    public static <T> BaseResponse<T> fail(String message) {
        return new BaseResponse<>(false, message, null);
    }
}
