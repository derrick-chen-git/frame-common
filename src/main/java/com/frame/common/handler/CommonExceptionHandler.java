package com.frame.common.handler;

import com.frame.common.base.ResponseData;
import com.frame.common.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseData exceptionHandler(Exception ex){
        log.error("request exception msg:{}",ex.getMessage());
        return new ResponseData(ResultCodeEnum.ERROR.getCode(),ex.getMessage());
    }
}
