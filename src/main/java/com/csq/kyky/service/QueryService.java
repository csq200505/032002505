package com.csq.kyky.service;


import com.csq.kyky.entity.GetDefiniteDto;

import java.util.List;
import java.util.Map;

/**
 * 查询接口服务
 *
 * @author csq
 * @date 2022/9/13
 */
public interface QueryService {
    /**
     * 获取七日内确诊数量的变化
     */
    List<Map> getDefiniteInSevenDays();

    /**
     * 获取七日内无症状数量的变化
     */
    List<Map> getIndefiniteInSevenDays();
    /**
     * 获取七日内确诊最多的10个省份
     */
    List<Map> getRank();

    /**
     * 获取地图数据（当日全国数据变化）
     */
    List<Map> getNationalMapData();

    /**
     * 根据省号和头尾时间获取图表
     */
    List<Map> getDefiniteBetweenDaysAndProvince(GetDefiniteDto data);
}
