package com.csq.kyky.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csq.kyky.entity.AccessURL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ,,,
 *
 * @author csq
 * @since 2022/9/10
 */
@Mapper
public interface AccessURLMapper extends BaseMapper<AccessURL> {

    @Select("Select `url` from `access_url`")
    List<String> selectAll();
}
