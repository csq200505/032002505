package com.csq.kyky.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 需要进行爬取的URL列表
 *
 * @author csq
 * @since 2022/9/10
 */
@Data
@TableName(value = "access_url")
@NoArgsConstructor
@AllArgsConstructor
public class AccessURL implements Serializable {
    @TableId(value = "url")
    private String accessURL;
}
