package com.csq.kyky.service;

import com.alibaba.excel.EasyExcel;
import com.csq.kyky.dao.DailyProvincialDataMapper;
import com.csq.kyky.dao.DailyTotalDataMapper;
import com.csq.kyky.entity.DailyProvincialData;
import com.csq.kyky.entity.DailyTotalData;
import com.csq.kyky.entity.ExcelOutput;
import com.csq.kyky.utils.ProvinceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 生成excel表
 *
 * @author csq
 * @date 2022/9/16
 */
@Service
public class ExcelExportService {

    public static List<ExcelOutput> excelOutputList;


    @Autowired
    private DailyTotalDataMapper dailyTotalDataMapper;

    @Autowired
    private DailyProvincialDataMapper dailyProvincialDataMapper;

    public List<ExcelOutput> getExcelOutput(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<ExcelOutput>outputs = new ArrayList<>();
        List<DailyTotalData> totalDataList = dailyTotalDataMapper.getTotalList();//获取所有每日数据
        totalDataList.forEach(total -> {  //遍历每日全国数据
                    List<DailyProvincialData> provincialList = dailyProvincialDataMapper.getDataByDate(total.getDataDate());

                    //新建两行表格
                    ExcelOutput excelOutput1 = new ExcelOutput();
                    ExcelOutput excelOutput2 = new ExcelOutput();
                    //设置日期
                    excelOutput1.setDate(simpleDateFormat.format(total.getDataDate()));
                    excelOutput2.setDate(simpleDateFormat.format(total.getDataDate()));
                    //设置状态
                    excelOutput1.setSymptom("确诊");
                    excelOutput2.setSymptom("无症状");
                    //设置全国新增数据
                    excelOutput1.setNation(total.getDefiniteAmount());
                    excelOutput2.setNation(total.getIndefiniteAmount());

                    if (!provincialList.isEmpty()){
                        provincialList.forEach(province -> {  //遍历当日各省数据
                            switch (ProvinceData.getProvinceNameByCode(province.getCode())) {//省份编码转为对应的省份名
                                case "北京":
                                    //设置各省新增数据
                                    excelOutput1.setBeijing(province.getDefiniteAmount());
                                    excelOutput2.setBeijing(province.getIndefiniteAmount());
                                    break;
                                case "天津":
                                    excelOutput1.setTianjin(province.getDefiniteAmount());
                                    excelOutput2.setTianjin(province.getIndefiniteAmount());
                                    break;
                                case "河北":
                                    excelOutput1.setHebei(province.getDefiniteAmount());
                                    excelOutput2.setHebei(province.getIndefiniteAmount());
                                    break;
                                case "山西":
                                    excelOutput1.setShanxi(province.getDefiniteAmount());
                                    excelOutput2.setShanxi(province.getIndefiniteAmount());
                                    break;
                                case "内蒙古":
                                    excelOutput1.setNeimenggu(province.getDefiniteAmount());
                                    excelOutput2.setNeimenggu(province.getIndefiniteAmount());
                                    break;
                                case "辽宁":
                                    excelOutput1.setLiaoning(province.getDefiniteAmount());
                                    excelOutput2.setLiaoning(province.getIndefiniteAmount());
                                    break;
                                case "吉林":
                                    excelOutput1.setJilin(province.getDefiniteAmount());
                                    excelOutput2.setJilin(province.getIndefiniteAmount());
                                    break;

                                case "黑龙江":
                                    excelOutput1.setHeilongjiang(province.getDefiniteAmount());
                                    excelOutput2.setHeilongjiang(province.getIndefiniteAmount());
                                    break;
                                case "上海":
                                    excelOutput1.setShanghai(province.getDefiniteAmount());
                                    excelOutput2.setShanghai(province.getIndefiniteAmount());
                                    break;
                                case "江苏":
                                    excelOutput1.setJiangsu(province.getDefiniteAmount());
                                    excelOutput2.setJiangsu(province.getIndefiniteAmount());
                                    break;
                                case "浙江":
                                    excelOutput1.setZhejiang(province.getDefiniteAmount());
                                    excelOutput2.setZhejiang(province.getIndefiniteAmount());
                                    break;
                                case "安徽":
                                    excelOutput1.setAnhui(province.getDefiniteAmount());
                                    excelOutput2.setAnhui(province.getIndefiniteAmount());
                                    break;
                                case "福建":
                                    excelOutput1.setFujian(province.getDefiniteAmount());
                                    excelOutput2.setFujian(province.getIndefiniteAmount());
                                    break;
                                case "江西":
                                    excelOutput1.setJiangxi(province.getDefiniteAmount());
                                    excelOutput2.setJiangxi(province.getIndefiniteAmount());
                                    break;

                                case "山东":
                                    excelOutput1.setShandong(province.getDefiniteAmount());
                                    excelOutput2.setShandong(province.getIndefiniteAmount());
                                    break;
                                case "河南":
                                    excelOutput1.setHenan(province.getDefiniteAmount());
                                    excelOutput2.setHenan(province.getIndefiniteAmount());
                                    break;
                                case "湖北":
                                    excelOutput1.setHubei(province.getDefiniteAmount());
                                    excelOutput2.setHubei(province.getIndefiniteAmount());
                                    break;
                                case "湖南":
                                    excelOutput1.setHunan(province.getDefiniteAmount());
                                    excelOutput2.setHunan(province.getIndefiniteAmount());
                                    break;
                                case "广东":
                                    excelOutput1.setGuangdong(province.getDefiniteAmount());
                                    excelOutput2.setGuangdong(province.getIndefiniteAmount());
                                    break;
                                case "广西":
                                    excelOutput1.setGuangxi(province.getDefiniteAmount());
                                    excelOutput2.setGuangxi(province.getIndefiniteAmount());
                                    break;
                                case "海南":
                                    excelOutput1.setHainan(province.getDefiniteAmount());
                                    excelOutput2.setHainan(province.getIndefiniteAmount());
                                    break;
                                case "重庆":
                                    excelOutput1.setChongqing(province.getDefiniteAmount());
                                    excelOutput2.setChongqing(province.getIndefiniteAmount());
                                    break;

                                case "四川":
                                    excelOutput1.setSichuan(province.getDefiniteAmount());
                                    excelOutput2.setSichuan(province.getIndefiniteAmount());
                                    break;
                                case "贵州":
                                    excelOutput1.setGuizhou(province.getDefiniteAmount());
                                    excelOutput2.setGuizhou(province.getIndefiniteAmount());
                                    break;
                                case "云南":
                                    excelOutput1.setYunnan(province.getDefiniteAmount());
                                    excelOutput2.setYunnan(province.getIndefiniteAmount());
                                    break;
                                case "西藏":
                                    excelOutput1.setXizang(province.getDefiniteAmount());
                                    excelOutput2.setXizang(province.getIndefiniteAmount());
                                    break;
                                case "陕西":
                                    excelOutput1.setShanxi2(province.getDefiniteAmount());
                                    excelOutput2.setShanxi2(province.getIndefiniteAmount());
                                    break;
                                case "甘肃":
                                    excelOutput1.setGansu(province.getDefiniteAmount());
                                    excelOutput2.setGansu(province.getIndefiniteAmount());
                                    break;
                                case "青海":
                                    excelOutput1.setQinghai(province.getDefiniteAmount());
                                    excelOutput2.setQinghai(province.getIndefiniteAmount());
                                    break;

                                case "宁夏":
                                    excelOutput1.setNingxia(province.getDefiniteAmount());
                                    excelOutput2.setNingxia(province.getIndefiniteAmount());
                                    break;
                                case "新疆":
                                    excelOutput1.setXinjiang(province.getDefiniteAmount());
                                    excelOutput2.setXinjiang(province.getIndefiniteAmount());
                                    break;
                                default:
                                    break;
                            }
                        });
                }
            //设置港澳台累计数据
            excelOutput1.setTaiwan(total.getTwTotal());
            excelOutput1.setHk(total.getHkTotal());
            excelOutput1.setMacao(total.getMacaoTotal());
            //插入这两行表格
            outputs.add(excelOutput1);
            outputs.add(excelOutput2);
        });
        return outputs;
    }
    //输出excel文件到前端
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String timeWord = simpleDateFormat.format(new Date());
        String fileName = "疫情数据截止_"+timeWord+".xlsx";
        List<ExcelOutput>list = excelOutputList;
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), ExcelOutput.class).sheet("合伙人业务订单").doWrite(list);
        response.flushBuffer();
    }

}
