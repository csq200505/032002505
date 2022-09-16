package com.csq.kyky.spider;

import com.csq.kyky.dao.DailyArticleMapper;
import com.csq.kyky.dao.DailyTotalDataMapper;
import com.csq.kyky.entity.DailyArticle;
import com.csq.kyky.entity.DailyTotalData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
@Component
@Order(99)
public class DataHandler implements ApplicationRunner {
    @Autowired
    private DailyArticleMapper dailyArticleMapper;

    @Autowired
    private DailyTotalDataMapper dailyTotalDataMapper;

    public void handleTotalData() {
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
        List<DailyArticle> dailyArticleList = dailyArticleMapper.getDailyArticleList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.DATE, 30);
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
                DailyTotalData dailyTotalData = new DailyTotalData();
                dailyTotalData.setDataDate(article.getDate());
                if (m11.find()) {
//            System.out.println("Found value: " + m1.group(0) );
//            System.out.println("Found value: " + m1.group(1) );
//            System.out.println("Found value: " + m1.group(2) );

                    System.out.println("新增本土确诊：" + m11.group(3));
                    dailyTotalData.setDefiniteAmount(Integer.parseInt(m11.group(3)));
                    System.out.println("新增各省份确诊：" + m11.group(4));


                } else if (m12.find()) {

                    System.out.println("新增本土确诊：" + m12.group(3));
                    dailyTotalData.setDefiniteAmount(Integer.parseInt(m12.group(3)));
                    System.out.println("新增各省份确诊：" + m12.group(4));


                } else {
                    System.out.println("NO MATCH1");
                }


                if (m22.find()) {
                    //           System.out.println("Found value: " + m2.group(0) );
//            System.out.println("Found value: " + m2.group(1) );
//            System.out.println("Found value: " + m2.group(2) );
                    System.out.println("新增本土无症状感染者：" + m22.group(3));
                    dailyTotalData.setIndefiniteAmount(Integer.parseInt(m22.group(3)));
                    System.out.println("新增各省份无症状感染者：" + m22.group(4));


                } else if (m21.find()) {
                    System.out.println("新增本土无症状感染者：" + m21.group(3));
                    dailyTotalData.setIndefiniteAmount(Integer.parseInt(m21.group(3)));
                    System.out.println("新增各省份无症状感染者：" + m21.group(4));


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
            }
        });
    }


    public void handleProvinceData(){


    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //handleTotalData();
        handleProvinceData();
    }
}