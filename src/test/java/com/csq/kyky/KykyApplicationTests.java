package com.csq.kyky;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csq.kyky.dao.DailyArticleMapper;
import com.csq.kyky.dao.DailyProvincialDataMapper;
import com.csq.kyky.dao.DailyTotalDataMapper;
import com.csq.kyky.entity.DailyArticle;
import com.csq.kyky.entity.DailyProvincialData;
import com.csq.kyky.entity.DailyTotalData;
import com.csq.kyky.utils.ProvinceData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.poifs.filesystem.Entry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class KykyApplicationTests {

    @Autowired
    private DailyArticleMapper dailyArticleMapper;

    @Autowired
    private DailyProvincialDataMapper dailyProvincialDataMapper;

    @Autowired
    private DailyTotalDataMapper dailyTotalDataMapper;

    @Test
    void contextLoads() {
    }

    //测试正则表达式匹配是否准确，测试2021年2月以来的所有数据
    @Test
    public void doTest() {

        List<DailyArticle> dailyArticleList = dailyArticleMapper.getDailyArticleList();
        String pattern11 = "新增确诊病例(\\d+)例(.*)本土病例(\\d+)例（(.*)），含(\\d+)例由无症状感染者转为确诊病例";
        String pattern12 = "新增确诊病例(\\d+)例(.*)本土病例(\\d+)例（(.*)）(。|；)无新增死亡病例";
        String pattern21 = "新增无症状感染者(\\d+)例(.*)本土(\\d+)例（(.*)）；(当日转为确诊病例(\\d+)例|当日无转为确诊病例)";
        String pattern22 = "新增无症状感染者(\\d+)例(.*)本土(\\d+)例（(.*)）。 当日解除医学观察的无症状感染者(\\d+)例";
        String pattern3 = "香港特别行政区(\\d+)例（(.*)），澳门特别行政区(\\d+)例（(.*)），台湾地区(\\d+)例（(.*)）。";


        // 创建 Pattern 对象
        Pattern r11 = Pattern.compile(pattern11);
        Pattern r12 = Pattern.compile(pattern12);
        Pattern r21 = Pattern.compile(pattern21);
        Pattern r22 = Pattern.compile(pattern22);
        Pattern r3 = Pattern.compile(pattern3);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 7);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date diffTime = calendar.getTime();

        dailyArticleList.forEach(article -> {
            article.setDate(new Date(article.getDate().getTime() - 86400000));
            log.info(simpleDateFormat.format(article.getDate()));
            Matcher m11 = r11.matcher(article.getContent());
            Matcher m12 = r12.matcher(article.getContent());
            Matcher m21 = r21.matcher(article.getContent());
            Matcher m22 = r22.matcher(article.getContent());
            Matcher m3 = r3.matcher(article.getContent());
            if (article.getDate().after(diffTime)) {
                if (m11.find()) {
//            System.out.println("Found value: " + m1.group(0) );
//            System.out.println("Found value: " + m1.group(1) );
//            System.out.println("Found value: " + m1.group(2) );
                    System.out.println("新增本土确诊：" + m11.group(3));
                    System.out.println("新增各省份确诊：" + m11.group(4));
                } else if (m12.find()) {
                    System.out.println("新增本土确诊：" + m12.group(3));
                    System.out.println("新增各省份确诊：" + m12.group(4));
                } else {
                    System.out.println("NO MATCH1");
                }

                if (m22.find()) {
                    //           System.out.println("Found value: " + m2.group(0) );
//            System.out.println("Found value: " + m2.group(1) );
//            System.out.println("Found value: " + m2.group(2) );
                    System.out.println("新增本土无症状感染者：" + m22.group(3));
                    System.out.println("新增各省份无症状感染者：" + m22.group(4));
                } else if (m21.find()) {
                    System.out.println("新增本土无症状感染者：" + m21.group(3));
                    System.out.println("新增各省份无症状感染者：" + m21.group(4));
                } else {
                    System.out.println("NO MATCH2");
                }

                if (m3.find()) {
//            System.out.println("Found value: " + m3.group(0) );
                    System.out.println("香港特别行政区累计：" + m3.group(1));
//            System.out.println("Found value: " + m3.group(2) );
                    System.out.println("澳门特别行政区累计：" + m3.group(3));
//            System.out.println("新增各省份无症状感染者: " + m3.group(4) );
                    System.out.println("台湾地区累计：" + m3.group(5));
//            System.out.println("Found value: " + m3.group(6) );
                } else {
                    System.out.println("NO MATCH3");
                }
                log.info("=== end ===");
            }
        });
    }//测试结果符合预期，个别特殊情况将手动录入数据库


    /**
     * 测试近七日内地新增确诊最多的十个省份
     * 测试时间2022-9-17
     * 测试时间段2022-9-10至2022-9-16
     */
    @Test
    public void doTest2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.YEAR, 2022);
        c1.set(Calendar.MONTH, 8);
        c1.set(Calendar.DATE, 9);
        c1.set(Calendar.HOUR, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        Date diffTime1 = c1.getTime();

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, 2022);
        c2.set(Calendar.MONTH, 8);
        c2.set(Calendar.DATE, 16);
        c2.set(Calendar.HOUR, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        Date diffTime2 = c2.getTime();

        LambdaQueryWrapper<DailyProvincialData> queryWrapper = new LambdaQueryWrapper<DailyProvincialData>()
                .between(DailyProvincialData::getDate,diffTime1,diffTime2);

        List<DailyProvincialData> dailyProvincialDataList = dailyProvincialDataMapper.selectList(queryWrapper);

        Map<Integer,Integer> map = new HashMap<>();

        dailyProvincialDataList.forEach(province -> {
            if(map.containsKey(province.getCode())){
                map.put(province.getCode(),map.get(province.getCode())+province.getDefiniteAmount());
            }else{
                map.put(province.getCode(),province.getDefiniteAmount());
            }
        });

        Stream<Map.Entry<Integer,Integer>> sorted = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue());

        Deque<Map.Entry<Integer, Integer>>stack = new LinkedList<>();

        sorted.forEach(stack::push);
        int count = 10;
        while(stack.size()>0&&count>0){
            Map.Entry<Integer,Integer> m= stack.pop();
            String name = ProvinceData.getProvinceNameByCode(m.getKey());
            System.out.print(name);
            System.out.print(" ");
            System.out.println(m.getValue());
            count--;
        }
    }//测试结果与9月17日的数据大屏下七日内新增前十名板块数据相同


    /**
     *2021年2月之前的数据 用正则表达式匹配数据并入库
     * 由于今后项目运行时不会用到这个时间段内的正则表达式
     * 所以我将这段时间的数据入库操作直接写在测试类里
     */
    @Test
    public void doTest3() {
        List<DailyArticle> dailyArticleList = dailyArticleMapper.getDailyArticleList();
        String pattern1 = "新增确诊病例(\\d+)例(.*)本土病例(\\d+)例（(.*)）；无新增死亡病例";
        String pattern2 = "新增无症状感染者(\\d+)例（境外输入(\\d+)例）；(当日转为确诊病例(\\d+)例|当日无转为确诊病例)";
        String pattern3 = "香港特别行政区(\\d+)例（(.*)），澳门特别行政区(\\d+)例（(.*)），台湾地区(\\d+)例（(.*)）。";
        String pattern4 = "(\\D+)(\\d+)例";

        // 创建 Pattern 对象
        Pattern r1 = Pattern.compile(pattern1);
        Pattern r2 = Pattern.compile(pattern2);
        Pattern r3 = Pattern.compile(pattern3);
        Pattern r4 = Pattern.compile(pattern4);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.YEAR, 2021);
        c1.set(Calendar.MONTH, 1);
        c1.set(Calendar.DATE, 7);
        c1.set(Calendar.HOUR, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        Date diffTime1 = c1.getTime();

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, 2020);
        c2.set(Calendar.MONTH, 4);
        c2.set(Calendar.DATE, 12);
        c2.set(Calendar.HOUR, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        Date diffTime2 = c2.getTime();

        dailyArticleList.forEach(article -> {
            article.setDate(new Date(article.getDate().getTime() - 86400000));
            log.info(simpleDateFormat.format(article.getDate()));
            Matcher m1 = r1.matcher(article.getContent());
            Matcher m2 = r2.matcher(article.getContent());
            Matcher m3 = r3.matcher(article.getContent());
            if (article.getDate().before(diffTime1) && article.getDate().after(diffTime2)) {
                DailyTotalData dailyTotalData = new DailyTotalData();
                dailyTotalData.setDataDate(article.getDate());
                String[] provincialDefinite = {};
                if (m1.find()) {
                    System.out.println("新增本土确诊：" + m1.group(3));
                    dailyTotalData.setDefiniteAmount(Integer.parseInt(m1.group(3)));
                    System.out.println("新增各省份确诊：" + m1.group(4));
                    provincialDefinite = m1.group(4).split("，|、|；");
                } else {
                    System.out.println("NO MATCH1");
                }

                if (m2.find()) {
                    int num, num1, num2;
                    num1= Integer.parseInt(m2.group(1));
                    num2= Integer.parseInt(m2.group(2));
                    num=num1-num2;
                    System.out.println("新增本土无症状感染者：" + num);
                    dailyTotalData.setIndefiniteAmount(num);
                } else {
                    System.out.println("NO MATCH2");
                }

                if (m3.find()) {
//            System.out.println("Found value: " + m3.group(0) );
                    System.out.println("香港特别行政区累计：" + m3.group(1));
                    dailyTotalData.setHkTotal(Integer.parseInt(m3.group(1)));
//            System.out.println("Found value: " + m3.group(2) );
                    System.out.println("澳门特别行政区累计：" + m3.group(3));
                    dailyTotalData.setMacaoTotal(Integer.parseInt(m3.group(3)));
//            System.out.println("新增各省份无症状感染者: " + m3.group(4) );
                    System.out.println("台湾地区累计：" + m3.group(5));
                    dailyTotalData.setTwTotal(Integer.parseInt(m3.group(5)));
//            System.out.println("Found value: " + m3.group(6) );
                } else {
                    System.out.println("NO MATCH3");
                }
                try {
                    dailyTotalDataMapper.insert(dailyTotalData);
                } catch (Exception e) {
                }
                log.info("=== end ===");
                for (String pro : provincialDefinite) {
                    Matcher m4 = r4.matcher(pro);
                    if(m4.find()&&ProvinceData.getProvinceCodeByName(m4.group(1))!=null){
                        DailyProvincialData dailyProvincialData = new DailyProvincialData();
                        dailyProvincialData.setDate(article.getDate());
                        dailyProvincialData.setCode(ProvinceData.getProvinceCodeByName(m4.group(1)));
                        dailyProvincialData.setDefiniteAmount(Integer.parseInt(m4.group(2)));
                        try {
                            dailyProvincialDataMapper.insert(dailyProvincialData);
                        }catch(Exception e){}
                    }
                }
            }
        });
    }
}


