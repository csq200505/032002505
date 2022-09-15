package com.csq.kyky.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 每日通报文章
 *
 * @author csq
 * @since 2022/9/11
 */
@TableName(value = "daily_article")
@Data
public class DailyArticle implements Serializable {
    @TableId("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @TableField(value = "content")
    private String content;
}
