package com.csq.kyky.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 每日全国数据
 *
 * @author csq
 * @date 2022/9/7
 */
@Data
@TableName(value = "daily_total_data")
public class DailyTotalData implements Serializable {

    /**
     * 数据截止日期（至xxxx年xx月xx日）
     */
    @TableId(value = "data_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataDate;


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
     * 香港总计
     */
    @TableField(value = "hk_total")
    private int hkTotal;

    /**
     * 澳门总计
     */
    @TableField(value = "macao_total")
    private int macaoTotal;

    /**
     * 台湾总计
     */
    @TableField(value = "tw_total")
    private int twTotal;



}
