package com.csq.kyky.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csq.kyky.utils.UUIDUtils;
import lombok.Data;

import java.util.Date;

/**
 * 每日省份数据
 *
 * @author csq
 * @date 2022/9/12
 */
@Data
@TableName("daily_provincial_data")
public class DailyProvincialData {

    @TableId(value = "id")
    private String id = UUIDUtils.generate();

    /**
     * 省份编码
     */
    @TableField(value = "code")
    private int code;

    /**
     * 新增确诊数
     */
    @TableField(value = "definite_amount")
    private int definiteAmount = 0;

    /**
     * 新增无症状
     */
    @TableField(value = "indefinite_amount")
    private int indefiniteAmount = 0;

    /**
     * 记录时间
     */
    @TableField(value = "date")
    private Date date;

}
