package com.csq.kyky.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

/**
 * 随机数生成器
 *
 * @author csq
 * @since 2022/9/9
 */

//@AllArgsConstructor 全参构造器
//@NoArgsConstructor  无参构造器
//@Data               get() set( )
public class RandomIntegerGenerator {
    /**
     * 随机整数生成器
     * @param min 开始数
     * @param max 结尾数
     * @return 生成值
     */

    public static int generate(int min, int max){
        Random random = new Random();
        return random.nextInt(max-min+1)+min;
    }
}
