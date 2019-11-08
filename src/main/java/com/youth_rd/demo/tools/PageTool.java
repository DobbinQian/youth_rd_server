package com.youth_rd.demo.tools;

import java.util.List;

public class PageTool<T> {

    public static <T> List<T> getDateByCL(List<T> allDate,Integer curr,Integer limit){
        if(allDate.size()>curr*limit){
            return allDate.subList((curr-1)*limit,curr*limit);
        }else if(allDate.size()<(curr-1)*limit){
            return null;
        }
        else{
            return allDate.subList((curr-1)*limit,allDate.size());
        }
    }
}
