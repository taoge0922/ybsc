package com.kelly.config;

import com.kelly.vo.AjaxReturn;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(Exception.class)
    public AjaxReturn<String> processException(Exception e) {
        return AjaxReturn.error(e.getMessage());
    }
}
