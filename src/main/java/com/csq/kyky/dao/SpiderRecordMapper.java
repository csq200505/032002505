package com.csq.kyky.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csq.kyky.entity.SpiderRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.lang.Nullable;

import java.util.List;


/**
 * ,,,
 *
 * @author csq
 * @since 2022/9/10
 */
@Mapper
public interface SpiderRecordMapper extends BaseMapper<SpiderRecord> {

    @Nullable
    @Select("Select * from `spider_record` where `spider_level` = #{level} order by `operate_time` desc")
    List<SpiderRecord> querySpiderRecordByTimeOrder(int level);

}
