package com.csq.kyky.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 确诊数据查询载体
 *
 * @author csq
 * @date 2022/9/15
 */
@Data
public class GetDefiniteDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private int code;
}
