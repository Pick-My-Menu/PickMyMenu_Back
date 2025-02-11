package com.pmm.pickmymenu_back.global;

import com.pmm.pickmymenu_back.dto.BaseResponse;
import com.pmm.pickmymenu_back.exception.MemberException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public BaseResponse<String> handleMemberException(Exception e, WebRequest req) throws MemberException{
        log.error("Exception : {}", e.getMessage());
        log.error("Uri : {}", req.getDescription(false));
        return BaseResponse.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<String> handleException(Exception e, WebRequest req) throws Exception{
        log.error("Exception : {}", e.getMessage());
        log.error("Uri : {}", req.getDescription(false));
        return BaseResponse.fail(e.getMessage());
    }
}
