package com.csq.kyky.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 *
 * @author csq
 * @date 2022/9/13
 */
@RestControllerAdvice
@Slf4j
public class ResponseAdvice {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response handlerException(Exception e){
        log.error(e.getMessage());
        return new Response<>().fail(e.getMessage());
    }
}
