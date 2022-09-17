package com.csq.kyky.controllers;


import com.csq.kyky.entity.GetDefiniteDto;
import com.csq.kyky.service.ExcelExportService;
import com.csq.kyky.service.QueryService;
import com.csq.kyky.utils.ProvinceData;
import com.csq.kyky.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 接口类
 *
 * @author csq
 * @date 2022/9/13
 */
@RestController
@CrossOrigin
public class GeneralController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private ExcelExportService excelExportService;

    /**
     * 获取全国省份列表
     * @return
     */
    @RequestMapping("/getDistricts")
    public Response<List<Map>> getDistricts(){
        List<Map>list = ProvinceData.getAllProvinceInfo();
        return new Response<List<Map>>().success(list);
    }

    /**
     * 获取七日内确诊数量的变化
     */
    @RequestMapping("/getDefiniteInSevenDays")
    public Response<List<Map>> getDefiniteInSevenDays(){
        return new Response<List<Map>>().success(queryService.getDefiniteInSevenDays());
    }

    /**
     * 获取七日内无症状数量的变化
     */
    @RequestMapping("/getIndefiniteInSevenDays")
    public Response<List<Map>> getIndefiniteInSevenDays(){
        return new Response<List<Map>>().success(queryService.getIndefiniteInSevenDays());
    }

    /**
     * 获取七日内确诊最多的10个省份
     */
    @RequestMapping("/getRank")
    public Response<List<Map>> getRank(){
        return new Response<List<Map>>().success(queryService.getRank());
    }

    /**
     * 获取地图数据（当日全国数据变化）
     */
    @RequestMapping("/getNationalMapData")
    public Response<List<Map>> getNationalMapData(){
        return new Response<List<Map>>().success(queryService.getNationalMapData());
    }

    /**
     * 根据始末时间和省份获取列表
     */
    @RequestMapping("/getDefiniteBetweenDaysAndProvince")
    public Response<List<Map>> getDefiniteBetweenDaysAndProvince(@RequestBody GetDefiniteDto data){
        return new Response<List<Map>>().success(queryService.getDefiniteBetweenDaysAndProvince(data));
    }

    /**
     *
     */
    @RequestMapping("/export")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        excelExportService.exportExcel(request,response);
    }
}
