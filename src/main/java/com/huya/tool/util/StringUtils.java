package com.huya.tool.util;

public class StringUtils {
    public static Boolean isEmpty(String str){
        return str == null || "".equals(str);
    }
    public static Boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
