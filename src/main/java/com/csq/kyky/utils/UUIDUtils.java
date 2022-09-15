package com.csq.kyky.utils;

import java.util.Locale;
import java.util.UUID;

/**
 * ,,,
 *
 * @author csq
 * @date 2022/9/10
 */
public class UUIDUtils {
    public static String generate(){
        return UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
    }
}
