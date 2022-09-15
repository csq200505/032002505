package com.csq.kyky.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csq.kyky.utils.UUIDUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ,,,
 *
 * @author csq
 * @since 2022/9/10
 */
@TableName("spider_record")
@Data
public class SpiderRecord implements Serializable {
    /**
     * 爬虫记录编号
     */
    @TableId(value ="id")
    private String id = UUIDUtils.generate();

    /**
     * 开始爬取时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(value = "operate_time")
    private Date operateTime;

    /**
     * 爬取结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 爬取成功数量
     */
    @TableField(value = "succeed_records")
    private int succeedRecords;

    /**
     * 爬取失败数量
     */
    @TableField(value = "failed_records")
    private int failedRecords;

    /**
     * 爬虫爬取目标数据等级：1-每日报告页面网址 2- 每日报告具体信息 3-第一级数据补偿（页面网址)
     */
    @TableField(value = "spider_level")
    private int spiderLevel;



}
