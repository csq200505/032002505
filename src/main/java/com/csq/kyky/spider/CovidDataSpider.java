package com.csq.kyky.spider;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csq.kyky.dao.AccessURLMapper;
import com.csq.kyky.dao.DailyArticleMapper;
import com.csq.kyky.dao.FailedURLMapper;
import com.csq.kyky.dao.SpiderRecordMapper;
import com.csq.kyky.entity.AccessURL;
import com.csq.kyky.entity.DailyArticle;
import com.csq.kyky.entity.FailedURL;
import com.csq.kyky.entity.SpiderRecord;
import com.csq.kyky.utils.RandomIntegerGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 疫情数据爬虫
 *
 * @author csq
 * @since 2022/9/9
 */

@Slf4j
@Component
public class CovidDataSpider {

    @Autowired
    private AccessURLMapper accessURLMapper;

    @Autowired
    private FailedURLMapper failedURLMapper;

    @Autowired
    private SpiderRecordMapper spiderRecordMapper;

    @Autowired
    private DailyArticleMapper dailyArticleMapper;

    @Autowired
    private DataHandler dataHandler;

    private static final String BASE_URL = "http://www.nhc.gov.cn";

    /**
     * 爬取页面
     * @param url
     * @return
     * @throws IOException
     */
    public Document spiderCatcher(String url) throws IOException {
        String[]proxyArray = HttpHeaderConsts.ipPool.get(RandomIntegerGenerator.generate(0,HttpHeaderConsts.ipPool.size()-1)).split(":");
        String userAgent = HttpHeaderConsts.HEADER_USER_AGENTS[RandomIntegerGenerator.generate(0,14)];
        return Jsoup.connect(url)
        //        .proxy(proxyArray[0],Integer.parseInt(proxyArray[1]))
                .userAgent(userAgent)
                .referrer(url)
                .header("Accept-Language","zh-CN,zh;q=0.9")
                .header("Connection","keep-alive")
                .ignoreContentType(true)
                .timeout(5000)
                .get();
    }

    /**
     * 爬取第一级：所有具体文章的页面信息
     */
    public void getAllSpiderWebSite(){
        SpiderRecord spiderRecord = new SpiderRecord();
        spiderRecord.setSpiderLevel(1);
        spiderRecord.setOperateTime(new Date());
        int successCount = 0;
        int failedCount = 0;
        if(!spiderRecordMapper.querySpiderRecordByTimeOrder(1).isEmpty()){
            String id = spiderRecordMapper.querySpiderRecordByTimeOrder(1).get(0).getId();
            List<FailedURL> failedURLs = failedURLMapper.queryFailedUrlBySpiderId(id);
            if(!failedURLs.isEmpty()) {
                AtomicInteger sc = new AtomicInteger(0);
                AtomicInteger fc = new AtomicInteger(0);
                failedURLs.parallelStream().forEach(failedURL -> {
                    if(doLevelOneAnalyse(failedURL.getURL(),spiderRecord)){
                        sc.getAndIncrement();
                    }else {
                        fc.getAndIncrement();
                    }
                });
                spiderRecord.setFailedRecords(fc.get());
                spiderRecord.setSucceedRecords(sc.get());
                spiderRecord.setEndTime(new Date());
                spiderRecordMapper.insert(spiderRecord);
            }
        }else {
            for (int i = 1; i <= 41; i++) {
                String url;
                if (i == 1) {
                    url = BASE_URL + "/xcs/yqtb/list_gzbd.shtml";

                } else {
                    url = BASE_URL + "/xcs/yqtb/list_gzbd_" + i + ".shtml";
                }
                if (doLevelOneAnalyse(url, spiderRecord)) {
                    successCount++;
                } else {
                    failedCount++;
                }
            }
            spiderRecord.setSucceedRecords(successCount);
            spiderRecord.setFailedRecords(failedCount);
            spiderRecord.setEndTime(new Date());
            spiderRecordMapper.insert(spiderRecord);
        }
    }

    /**
     * 爬取第二级：获取所有所需数据
     */
    public void getAllArticles(){
        log.info("=== 开始爬取第二级数据 ===");
        SpiderRecord spiderRecord = new SpiderRecord();
        spiderRecord.setSpiderLevel(2);
        spiderRecord.setOperateTime(new Date());
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failedCount = new AtomicInteger(0);
        if(!spiderRecordMapper.querySpiderRecordByTimeOrder(2).isEmpty()){
            String id = spiderRecordMapper.querySpiderRecordByTimeOrder(2).get(0).getId();
            List<FailedURL>failedURLs = failedURLMapper.queryFailedUrlBySpiderId(id);
            if(!failedURLs.isEmpty()) {
                failedURLs.parallelStream().forEach(failedURL -> {
                    if(doLevelTwoAnalyse(failedURL.getURL(),spiderRecord)){
                        successCount.getAndIncrement();
                    }else {
                        failedCount.getAndIncrement();
                    }
                });
                spiderRecord.setFailedRecords(failedCount.get());
                spiderRecord.setSucceedRecords(successCount.get());
                spiderRecord.setEndTime(new Date());
                spiderRecordMapper.insert(spiderRecord);
            }
        }else {
            List<String> allUrls = accessURLMapper.selectAll();
            allUrls.parallelStream().forEach(url -> {
                if(doLevelTwoAnalyse(url,spiderRecord)) {
                    successCount.getAndIncrement();
                }else {
                    failedCount.getAndIncrement();
                }
            });
            spiderRecord.setSucceedRecords(successCount.get());
            spiderRecord.setFailedRecords(failedCount.get());
            spiderRecord.setEndTime(new Date());
            spiderRecordMapper.insert(spiderRecord);
        }
        log.info("=== 第二级数据爬取结束 ===");
    }


    /**
     * 进行每日数据补偿任务
     */
    public boolean getNewWebsites(){
        log.info("=== 开始进行每日数据补偿任务 ===");
        SpiderRecord record = new SpiderRecord();
        record.setOperateTime(new Date());
        record.setSpiderLevel(3);
        int successCount = 0;
        int failedCount = 0;
        if(spiderRecordMapper.querySpiderRecordByTimeOrder(3).get(0).getFailedRecords()!=0){
            AtomicInteger sc = new AtomicInteger(0);
            AtomicInteger fc = new AtomicInteger(0);
            List<FailedURL> failedURLList = failedURLMapper.queryFailedUrlBySpiderId(spiderRecordMapper.querySpiderRecordByTimeOrder(3).get(0).getId());
            List<DailyArticle>dailyArticleList = new ArrayList<>();
            failedURLList.parallelStream().forEach(failedURL -> {
                DailyArticle dailyArticle = doLevelFourAnalyse(failedURL.getURL(), record);
                if(dailyArticle==null){
                    fc.getAndIncrement();
                }else{
                    sc.getAndIncrement();
                    dailyArticleList.add(dailyArticle);
                }
            });
            if(dailyArticleList.size()!=0) {
                dataHandler.handleTotalData(dailyArticleList);
            }
            record.setSucceedRecords(sc.get());
            record.setFailedRecords(fc.get());
            record.setEndTime(new Date());
            spiderRecordMapper.insert(record);
            if(fc.get()!=0){
                return false;
            }
            return true;
        }
        Document document;
        document = getLevelThreeDocument(1);
        //如果第一页无法获取，直接失败
        if(document==null){
            return false;
        }
        successCount++;
        List<AccessURL> accessURLs = new LinkedList<>();
        LevelThreeDataPkg pkg = doLevelThreeAnalyse(document,accessURLs);
        accessURLs = pkg.accessURLS;
        if(pkg.getElement().isEmpty()){
            int step = 2;
            boolean finished = false;
            do{
                document = getLevelThreeDocument(step);
                //同理，补偿后面页数失败也放弃并重新执行
                if(document == null) {
                    return false;
                }else{
                    successCount++;
                    pkg = doLevelThreeAnalyse(document,accessURLs);
                    if(pkg.getElement().isPresent()){
                        finished = true;
                    }else{
                        step++;
                    }
                }
            }
            while(!finished);
        }
        if(accessURLs.isEmpty()){
            log.info(" === 数据无需更新 ===");
            return true;
        }

        accessURLs.forEach(accessURL -> {
            accessURLMapper.insert(accessURL);
        });
        //全部补偿完成，开始进行子数据捕捉和分析
        AtomicInteger sc = new AtomicInteger(successCount);
        AtomicInteger fc = new AtomicInteger(failedCount);
        List<DailyArticle> dailyArticleList = new ArrayList<>();
        log.info(" === 开始进行具体数据获取，需获取总量为{}条 ===",accessURLs.size());
        accessURLs.parallelStream().forEach(url -> {
            DailyArticle dailyArticle = doLevelFourAnalyse(url.getAccessURL(), record);
            if(dailyArticle==null){
                fc.getAndIncrement();
            }else{
                sc.getAndIncrement();
                log.info("文章内容："+dailyArticle.getContent());
                dailyArticleList.add(dailyArticle);
            }
        });
        if(dailyArticleList.size()!=0) {
            dataHandler.handleTotalData(dailyArticleList);
        }
        record.setSucceedRecords(sc.get());
        record.setFailedRecords(fc.get());
        record.setEndTime(new Date());
        spiderRecordMapper.insert(record);
        if (fc.get() != 0) {
            return false;
        }
        return true;
    }


    /**
     * 进行第一级分析
     * @param url
     * @param record
     * @return
     */
    private boolean doLevelOneAnalyse(String url ,SpiderRecord record){
        try{
            Document document = spiderCatcher(url);
            document.getElementsByTag("ul").forEach(element -> {
                log.info(element.toString());
                element.getElementsByTag("li").forEach(li -> {
                    log.info("li:{}",li.toString());
                    String resultUrl = "";
                    resultUrl = BASE_URL+li.getElementsByTag("a").first().attr("href");
                    AccessURL accessURL = new AccessURL();
                    accessURL.setAccessURL(resultUrl);
                    accessURLMapper.insert(accessURL);
                });
            });
            return true;

        }catch (Exception e){
            FailedURL failedURL = new FailedURL();
            failedURL.setURL(url);
            failedURL.setSpiderId(record.getId());
            failedURLMapper.insert(failedURL);
            log.error("在请求时出现异常:",e);
            return false;
        }
    }

    /**
     * 进行第二级分析
     * @return
     */
    private boolean doLevelTwoAnalyse(String url, SpiderRecord record){
        try{
            Document document = spiderCatcher(url);
            Element timeElements = document.selectFirst("body > div.w1024.mb50 > div.list > div.source > span:nth-child(2)");
            Element contentElements = document.selectFirst("#xw_box");

            String dateTime = timeElements.text();
            String content = contentElements.getAllElements().first().text();

            String lists[] = dateTime.split("：");
            dateTime = lists[1];
            dateTime.replace(" ","");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date time = simpleDateFormat.parse(dateTime);
            DailyArticle dailyArticle = new DailyArticle();
            dailyArticle.setDate(time);
            dailyArticle.setContent(content);
            try {
                Thread.sleep(200);
                dailyArticleMapper.insert(dailyArticle);
            }catch (DuplicateKeyException e){
                log.info("重复数据！{}", dailyArticle.getDate());
            }
            log.info("爬取成功！");
            return true;

        }catch (Exception e){
            FailedURL failedURL = new FailedURL();
            failedURL.setURL(url);
            failedURL.setSpiderId(record.getId());
            failedURLMapper.insert(failedURL);
            log.error("爬取失败！");
            return false;
        }
    }

    /**
     * 第三级数据爬取
     */
    public Document getLevelThreeDocument(int step){
        Document document = null;
        boolean isSuccess = false;
        int trialTimes = 0;
        while(!isSuccess) {
            try {
                if(step==1){
                    document = spiderCatcher(BASE_URL + "/xcs/yqtb/list_gzbd.shtml");
                }else {
                    document = spiderCatcher(BASE_URL + "/xcs/yqtb/list_gzbd" + step + ".shtml");
                }
                isSuccess = true;
            } catch (Exception e) {
                if(trialTimes>10){
                    log.error(" === 数据补偿第{}页请求上限已到，放弃尝试...===",step);
                    return null;
                }else{
                    log.info(" === 数据补偿请求失败，重试中... ===",e);
                }
                trialTimes++;
            }
        }
        return document;
    }

    /**
     * 进行第三级分析
     */
    public LevelThreeDataPkg doLevelThreeAnalyse(Document document, List<AccessURL> accessURLs){
        return new LevelThreeDataPkg(document.getElementsByTag("ul").first().getElementsByTag("li").stream().filter(li -> {
            log.info("li:{}",li.toString());
            String resultUrl = "";
            resultUrl = BASE_URL+li.getElementsByTag("a").first().attr("href");
            if(!accessURLMapper.exists(new LambdaQueryWrapper<AccessURL>().eq(AccessURL::getAccessURL,resultUrl))){
                AccessURL url = new AccessURL(resultUrl);
                accessURLs.add(url);
                return false;
            }
            return true;
        }).findAny(),accessURLs);
    }

    /**
     * 进行四级数据分析
     * @param url
     * @param record
     * @return
     */
    private DailyArticle doLevelFourAnalyse(String url, SpiderRecord record){
        try{
            Document document = spiderCatcher(url);
            Element timeElements = document.selectFirst("body > div.w1024.mb50 > div.list > div.source > span:nth-child(2)");
            Element contentElements = document.selectFirst("#xw_box");

            String dateTime = timeElements.text();
            String content = contentElements.getAllElements().first().text();

            String lists[] = dateTime.split("：");
            dateTime = lists[1];
            dateTime.replace(" ","");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date time = simpleDateFormat.parse(dateTime);
            DailyArticle dailyArticle = new DailyArticle();
            dailyArticle.setDate(time);
            dailyArticle.setContent(content);
            try {
                Thread.sleep(200);
                dailyArticleMapper.insert(dailyArticle);
            }catch (DuplicateKeyException e){
                log.info("重复数据！{}", dailyArticle.getDate());
            }
            log.info("爬取成功！");
            return dailyArticle;

        }catch (Exception e){
            FailedURL failedURL = new FailedURL();
            failedURL.setURL(url);
            failedURL.setSpiderId(record.getId());
            failedURLMapper.insert(failedURL);
            log.error("爬取失败！");
            return null;
        }
    }


    @Data
    @AllArgsConstructor
    private class LevelThreeDataPkg{
        private Optional<Element> element;

        private List<AccessURL> accessURLS;
    }

}