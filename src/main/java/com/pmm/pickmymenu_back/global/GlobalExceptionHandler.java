package com.pmm.pickmymenu_back.global;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public BaseResponse<String> handleException(Exception e, WebRequest req) throws Exception{
        log.error("Exception : {}", e.getMessage());
        log.error("WebRequest : {}", req.getContextPath());
        return BaseResponse.fail(e.getMessage());
    }
}
