package com.csq.kyky.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csq.kyky.dao.DailyProvincialDataMapper;
import com.csq.kyky.dao.DailyTotalDataMapper;
import com.csq.kyky.entity.DailyProvincialData;
import com.csq.kyky.entity.DailyTotalData;
import com.csq.kyky.entity.GetDefiniteDto;
import com.csq.kyky.entity.StatisticReceiver;
import com.csq.kyky.service.QueryService;
import com.csq.kyky.utils.ProvinceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 查询服务实现类
 *
 * @author csq
 * @date 2022/9/13
 */
@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    private DailyTotalDataMapper dailyTotalDataMapper;

    @Autowired
    private DailyProvincialDataMapper dailyProvincialDataMapper;

    @Override
    public List<Map> getDefiniteInSevenDays() {
        List<Date> dateList = getDayRange();
        LambdaQueryWrapper<DailyTotalData> queryWrapper = new LambdaQueryWrapper<DailyTotalData>()
                .between(DailyTotalData::getDataDate,dateList.get(0),dateList.get(1))
                .orderBy(true,true,DailyTotalData::getDataDate);
        List<DailyTotalData> dailyTotalData = dailyTotalDataMapper.selectList(queryWrapper);
        List<Map> resultList = new ArrayList<>(dailyTotalData.size());
        dailyTotalData.forEach(data -> {
            Map result = new HashMap();
            result.put("key",dateFormatter(data.getDataDate()));
            result.put("value",data.getDefiniteAmount());
            resultList.add(result);
        });
        return resultList;
    }

    @Override
    public List<Map> getIndefiniteInSevenDays() {
        List<Date> dateList = getDayRange();
        LambdaQueryWrapper<DailyTotalData> queryWrapper = new LambdaQueryWrapper<DailyTotalData>()
                .between(DailyTotalData::getDataDate,dateList.get(0),dateList.get(1))
                .orderBy(true,true,DailyTotalData::getDataDate);
        List<DailyTotalData> dailyTotalData = dailyTotalDataMapper.selectList(queryWrapper);
        List<Map> resultList = new ArrayList<>(dailyTotalData.size());
        dailyTotalData.forEach(data -> {
            Map result = new HashMap();
            result.put("key", dateFormatter(data.getDataDate()));
            result.put("value",data.getIndefiniteAmount());
            resultList.add(result);
        });
        return resultList;
    }

    @Override
    public List<Map> getRank() {
        List<Date> dateList = getDayRange();
        List<StatisticReceiver> receiverList = dailyProvincialDataMapper.getStatisticInDays(dateList.get(0),dateList.get(1));
        List<Map>resultList = new ArrayList<>();
        receiverList.forEach(province -> {
            Map map = new HashMap();
            map.put("key", ProvinceData.getProvinceNameByCode(province.getCode()));
            map.put("value",province.getTotal());
            resultList.add(map);
        });
        return resultList;
    }

    @Override
    public List<Map> getNationalMapData() {
        Date time;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MILLISECOND,0);
        Calendar calendar11am = Calendar.getInstance();
        calendar11am.set(Calendar.SECOND,0);
        calendar11am.set(Calendar.MINUTE,0);
        calendar11am.set(Calendar.HOUR_OF_DAY,11);
        calendar11am.set(Calendar.MILLISECOND,0);
        if(new Date().before(calendar11am.getTime())){
            calendar.add(Calendar.DAY_OF_MONTH,-2);
            time = calendar.getTime();
        }else{
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            time = calendar.getTime();
        }

        DailyTotalData dailyTotalData = dailyTotalDataMapper.selectById(time);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        DailyTotalData yesterdayData = dailyTotalDataMapper.selectById(calendar.getTime());

        int hkIncrease = dailyTotalData.getHkTotal()-yesterdayData.getHkTotal();
        int macaoIncrease = dailyTotalData.getMacaoTotal()-yesterdayData.getMacaoTotal();
        int twIncrease = dailyTotalData.getTwTotal()-yesterdayData.getTwTotal();

        List<Map> provinceMap = ProvinceData.getAllProvinceInfo();
        List<Map> resultMap = new ArrayList<>();

        provinceMap.forEach(province -> {
            int code = (int)province.get("code");
            DailyProvincialData dailyProvincialData = dailyProvincialDataMapper.getDataByDateAndCode(time,code);
            if(dailyProvincialData==null){
                switch (code){
                    case 710000:province.put("value",twIncrease);break;
                    case 810000:province.put("value",hkIncrease);break;
                    case 820000:province.put("value",macaoIncrease);break;
                    default:province.put("value",0);break;
                }
            }else{
                province.put("value",dailyProvincialData.getDefiniteAmount());
            }
            resultMap.add(province);
        });

        return resultMap;
    }

    @Override
    public List<Map> getDefiniteBetweenDaysAndProvince(GetDefiniteDto data) {
        Date startDate = data.getStartTime();
        Date endDate = data.getEndTime();
        int province = data.getCode();
        LambdaQueryWrapper<DailyProvincialData> queryWrapper = new LambdaQueryWrapper<DailyProvincialData>()
                .between(DailyProvincialData::getDate, startDate,endDate)
                .eq(DailyProvincialData::getCode,province)
                .orderBy(true, true,DailyProvincialData::getDate);
        List<DailyProvincialData>list = dailyProvincialDataMapper.selectList(queryWrapper);
        List<Map>resultList = new ArrayList<>(list.size());
        list.forEach(result -> {
            Map map = new HashMap();
            map.put("key",dateFormatter(result.getDate()));
            map.put("value",result.getDefiniteAmount());
            resultList.add(map);
        });
        return resultList;
    }

    /**
     * 获得头尾2天的Date对象
     * @return
     */
    private List<Date> getDayRange(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MILLISECOND,0);
        Calendar calendar11am = Calendar.getInstance();
        calendar11am.set(Calendar.SECOND,0);
        calendar11am.set(Calendar.MINUTE,0);
        calendar11am.set(Calendar.HOUR_OF_DAY,11);
        calendar11am.set(Calendar.MILLISECOND,0);
        Date todayTime;
        Date previousTime;
        if(new Date().before(calendar11am.getTime())){
            calendar.add(Calendar.DAY_OF_MONTH,-2);
            todayTime = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH,-5);
            previousTime = calendar.getTime();
        }else{
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            todayTime = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH,-6);
            previousTime = calendar.getTime();
        }
        List<Date> dateList = new ArrayList<>(2);
        dateList.add(previousTime);
        dateList.add(todayTime);
        return dateList;
    }

    private String dateFormatter(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
