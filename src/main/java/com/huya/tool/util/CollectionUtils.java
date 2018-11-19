package com.huya.tool.util;

import java.util.List;

public class CollectionUtils {
    public Boolean isEmpty(List list){
        return list == null || list.size() == 0 ;
    }
    public Boolean isNotEmpty(List list){
        return !isEmpty(list);
    }
}
