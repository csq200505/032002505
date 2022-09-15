package com.csq.kyky.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csq.kyky.entity.DailyArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * ,,,
 *
 * @author csq
 * @since 2022/9/11
 */
@Mapper
public interface DailyArticleMapper extends BaseMapper<DailyArticle> {

    @Select("Select `date` from `daily_article` order by `date` desc")
    List<Date> getDailyArticle();
    @Select("Select * from `daily_article`")
    List<DailyArticle>getDailyArticleList();

}
