package com.csq.kyky.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * excel表格表头
 *
 * @author csq
 * @date 2022/9/16
 */
@Data
public class ExcelOutput implements Serializable {

    @ExcelProperty(value = "时间", index = 0)
    private String date;

    @ExcelProperty(value = "症状",index = 1)
    private String symptom;

    @ExcelProperty(value = "全国", index = 2)
    private int nation;

    @ExcelProperty(value = "北京",index = 3)
    private int beijing;

    @ExcelProperty(value = "天津", index = 4)
    private int tianjin;

    @ExcelProperty(value = "河北",index = 5)
    private int hebei;

    @ExcelProperty(value = "山西", index = 6)
    private int shanxi;

    @ExcelProperty(value = "内蒙古",index = 7)
    private int neimenggu;

    @ExcelProperty(value = "辽宁", index = 8)
    private int liaoning;

    @ExcelProperty(value = "吉林",index = 9)
    private int jilin;

    @ExcelProperty(value = "黑龙江", index = 10)
    private int heilongjiang;

    @ExcelProperty(value = "上海",index = 11)
    private int shanghai;

    @ExcelProperty(value = "江苏", index = 12)
    private int jiangsu;

    @ExcelProperty(value = "浙江",index = 13)
    private int zhejiang;

    @ExcelProperty(value = "安徽", index = 14)
    private int anhui;

    @ExcelProperty(value = "福建",index = 15)
    private int fujian;

    @ExcelProperty(value = "江西", index = 16)
    private int jiangxi;

    @ExcelProperty(value = "山东",index = 17)
    private int shandong;

    @ExcelProperty(value = "河南", index = 18)
    private int henan;

    @ExcelProperty(value = "湖北",index = 19)
    private int hubei;

    @ExcelProperty(value = "湖南", index = 20)
    private int hunan;

    @ExcelProperty(value = "广东",index = 21)
    private int guangdong;

    @ExcelProperty(value = "广西", index = 22)
    private int guangxi;

    @ExcelProperty(value = "海南",index = 23)
    private int hainan;

    @ExcelProperty(value = "重庆", index = 24)
    private int chongqing;

    @ExcelProperty(value = "四川",index = 25)
    private int sichuan;

    @ExcelProperty(value = "贵州", index = 26)
    private int guizhou;

    @ExcelProperty(value = "云南",index = 27)
    private int yunnan;

    @ExcelProperty(value = "西藏", index = 28)
    private int xizang;

    @ExcelProperty(value = "陕西",index = 29)
    private int shanxi2;

    @ExcelProperty(value = "甘肃", index = 30)
    private int gansu;

    @ExcelProperty(value = "青海",index = 31)
    private int qinghai;

    @ExcelProperty(value = "宁夏", index = 32)
    private int ningxia;

    @ExcelProperty(value = "新疆",index = 33)
    private int xinjiang;

    @ExcelProperty(value = "台湾累计", index = 34)
    private int taiwan;

    @ExcelProperty(value = "香港累计",index = 35)
    private int hk;

    @ExcelProperty(value = "澳门累计", index = 36)
    private int macao;

}
