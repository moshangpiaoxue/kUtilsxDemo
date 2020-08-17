package com.mo.libsx.utils.sort_utils;

import com.mo.libsx.modle.bean.CityBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/8/17:10:18
 * @ 功能： 排序工具类
 */
public class SortUtli {
    /**
     * 对城市列表排序
     */
    public static List<CityBean> CitySort(List<CityBean> data) {
        Collections.sort(data, new Comparator<CityBean>() {
            /** 排序规则 */
            @Override
            public int compare(CityBean o1, CityBean o2) {
                if (o1.getName().contains("定位")) {
                    return -1;
                }
                if (o2.getName().contains("定位")) {
                    return 1;
                }
                if (o1.getName().contains("热门")) {
                    return -1;
                }
                if (o2.getName().contains("热门")) {
                    return 1;
                }
                //当包含@时，我比谁都大
                if (o1.getName().contains("@")) {
                    return 1;
                }
                //当包含@时，谁都比我大
                if (o2.getName().contains("@")) {
                    return -1;
                }
                //当包含#时，我比谁都大
                if (o1.getName().contains("#")) {
                    return 1;
                }
                //当包含#时，谁都比我大
                if (o2.getName().contains("#")) {
                    return -1;
                } else {
                    return o1.getTopc().compareTo(o2.getTopc());
                }
            }
        });
        return data;
    }
}
