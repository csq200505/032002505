package com.csq.kyky.job;

import com.csq.kyky.service.ExcelExportService;
import com.csq.kyky.spider.CovidDataSpider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 数据更新处理
 *
 * @author csq
 * @since 2022/9/11
 */
@Slf4j
@Component
@Order(99)
public class DailyFetchDataJob implements ApplicationRunner {

    @Autowired
    private CovidDataSpider spider;

    @Autowired
    private ExcelExportService exportService;

    /**
     * 如果获取失败，则不停执行到完成为止
     * 运行时则固定每天10点进行数据补偿
     */
    @Scheduled(cron = "0 0 10 * * ?")
    public void handleGet() throws InterruptedException {
        boolean getResult;
        do{
            Thread.sleep(200);
            getResult = spider.getNewWebsites();
        }
        while(!getResult);
        ExcelExportService.excelOutputList = exportService.getExcelOutput();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        handleGet();
    }
}
