package com.csq.kyky.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csq.kyky.entity.DailyTotalData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ,,,
 *
 * @author csq
 * @date 2022/9/12
 */
@Mapper
public interface DailyTotalDataMapper extends BaseMapper<DailyTotalData> {
    @Select("Select * from `daily_total_data` order by `data_date` desc")
    List<DailyTotalData> getTotalList();
}
