package com.pmm.pickmymenu_back.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@NoArgsConstructor
public class BaseResponse<T> {

    private HttpStatusCode status;
    private Boolean success;
    private String message;
    private T data;

    public BaseResponse(HttpStatusCode status, Boolean success, String message, T data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(HttpStatus.OK, true, "Success", data);
    }

    public static <T> BaseResponse<T> fail(String message) {
        return new BaseResponse<>(HttpStatus.BAD_REQUEST, false, message, null);
    }
}
