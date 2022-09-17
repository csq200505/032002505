package com.csq.kyky.utils;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.*;

/**
 * 全国省份处理工具
 *
 * @author csq
 * @date 2022/9/11
 */
public class ProvinceData {

    @AllArgsConstructor
    public static class ProvinceNode implements Serializable {
        private int code;

        private String name;
    }

    private static Map<String, ProvinceNode> PROVINCE_MAP;

    private static void doPut(ProvinceNode node){
        PROVINCE_MAP.put(node.name,node);
        PROVINCE_MAP.put(String.valueOf(node.code),node);
    }

    static {
        PROVINCE_MAP = new HashMap<>();
        ProvinceNode node = new ProvinceNode(110000,"北京");
        doPut(node);
        node = new ProvinceNode(120000,"天津");
        doPut(node);
        node = new ProvinceNode(130000,"河北");
        doPut(node);
        node = new ProvinceNode(140000,"山西");
        doPut(node);
        node = new ProvinceNode(150000,"内蒙古");
        doPut(node);
        node = new ProvinceNode(210000,"辽宁");
        doPut(node);
        node = new ProvinceNode(220000,"吉林");
        doPut(node);
        node = new ProvinceNode(230000,"黑龙江");
        doPut(node);
        node = new ProvinceNode(310000,"上海");
        doPut(node);
        node = new ProvinceNode(320000,"江苏");
        doPut(node);
        node = new ProvinceNode(330000,"浙江");
        doPut(node);
        node = new ProvinceNode(340000,"安徽");
        doPut(node);
        node = new ProvinceNode(350000,"福建");
        doPut(node);
        node = new ProvinceNode(360000,"江西");
        doPut(node);
        node = new ProvinceNode(370000,"山东");
        doPut(node);
        node = new ProvinceNode(410000,"河南");
        doPut(node);
        node = new ProvinceNode(420000,"湖北");
        doPut(node);
        node = new ProvinceNode(430000,"湖南");
        doPut(node);
        node = new ProvinceNode(440000,"广东");
        doPut(node);
        node = new ProvinceNode(450000,"广西");
        doPut(node);
        node = new ProvinceNode(460000,"海南");
        doPut(node);
        node = new ProvinceNode(500000,"重庆");
        doPut(node);
        node = new ProvinceNode(510000,"四川");
        doPut(node);
        node = new ProvinceNode(520000,"贵州");
        doPut(node);
        node = new ProvinceNode(530000,"云南");
        doPut(node);
        node = new ProvinceNode(540000,"西藏");
        doPut(node);
        node = new ProvinceNode(610000,"陕西");
        doPut(node);
        node = new ProvinceNode(620000,"甘肃");
        doPut(node);
        node = new ProvinceNode(630000,"青海");
        doPut(node);
        node = new ProvinceNode(640000,"宁夏");
        doPut(node);
        node = new ProvinceNode(650000,"新疆");
        doPut(node);
        node = new ProvinceNode(710000,"台湾");
        doPut(node);
        node = new ProvinceNode(810000,"香港");
        doPut(node);
        node = new ProvinceNode(820000,"澳门");
        doPut(node);
    }

    /**
     * 根据省份名获取省份编码
     * @param name
     * @return
     */
    public static Integer getProvinceCodeByName(String name){
        ProvinceNode node = PROVINCE_MAP.get(name);
        if(node==null){
            //throw new IllegalArgumentException("这个省份不存在！");
            return null;
        }else{
            return node.code;
        }
    }

    /**
     * 根据省份编码获取省份名
     * @param code
     * @return
     */
    public static String getProvinceNameByCode(int code) {
        ProvinceNode node = PROVINCE_MAP.get(String.valueOf(code));
        if(node==null){
            //throw new IllegalArgumentException("这个省份不存在！");
            return null;
        }else{
            return node.name;
        }
    }

    /**
     * 获取各省份信息
     * @return
     */
    public static List<Map> getAllProvinceInfo(){
        List<Map> list = new LinkedList<>();
        Set<ProvinceNode>set = new HashSet<>();
        PROVINCE_MAP.values().forEach(content -> {
            set.add(content);
        });
        set.forEach(content->{
            Map map = new HashMap();
            map.put("code",content.code);
            map.put("name",content.name);
            list.add(map);
        });
        return list;
    }

}
