package com.csq.kyky.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csq.kyky.utils.UUIDUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * 爬取失败记录
 *
 * @author csq
 * @since 2022/9/10
 */
@TableName("failed_url")
@Data
public class FailedURL implements Serializable {
    @TableId(value = "id")
    private String id = UUIDUtils.generate();

    @TableField(value = "spider_id")
    private String spiderId;

    @TableField(value = "url")
    private String URL;
}
