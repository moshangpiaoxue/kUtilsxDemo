package com.mo.kutilsxdemo.modle;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/29:14:36
 * @ 功能：
 */
public class ListModle {

    public static List<String> getList(int lenth) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            list.add("测试=" + i);
        }
        return list;
    }
}
