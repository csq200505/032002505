package com.csq.kyky.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csq.kyky.entity.DailyProvincialData;
import com.csq.kyky.entity.StatisticReceiver;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * ,,,
 *
 * @author csq
 * @date 2022/9/12
 */
@Mapper
public interface DailyProvincialDataMapper extends BaseMapper<DailyProvincialData> {

    @Select("Select COUNT(*) from `daily_provincial_data` Where `date` = #{date} and `code` = #{code}")
    long countRecord(Date date, int code);

    @Select("Select * from `daily_provincial_data` Where `date` = #{date} and `code` = #{code}")
    DailyProvincialData getDataByDateAndCode(Date date, int code);

    @Select("SELECT `code`,SUM(`definite_amount`) as `total` FROM `daily_provincial_data` WHERE `date` " +
            "BETWEEN #{startDate} AND #{endDate} GROUP BY `code` ORDER BY SUM(`definite_amount`) DESC LIMIT 0,10")
    List<StatisticReceiver> getStatisticInDays(Date startDate, Date endDate);

    @Select("Select * from `daily_provincial_data` where `date` = #{date}")
    List<DailyProvincialData> getDataByDate(Date date);
}
