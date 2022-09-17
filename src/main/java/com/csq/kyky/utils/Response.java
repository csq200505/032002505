package com.csq.kyky.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 标准返回体
 *
 * @author csq
 * @since 2022/9/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date time;

    public Response<T> success(T data){
        return new Response<T>(200,"请求成功！",data,new Date());
    }

    public Response<T> fail(T data){
        return new Response<T>(500,"内部错误",data,new Date());
    }
}
