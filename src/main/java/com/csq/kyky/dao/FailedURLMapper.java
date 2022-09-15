package com.csq.kyky.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csq.kyky.entity.FailedURL;
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
public interface FailedURLMapper extends BaseMapper<FailedURL> {

    @Nullable
    @Select("Select * from failed_url Where `spider_id` = #{id}")
    List<FailedURL> queryFailedUrlBySpiderId(String id);
}
