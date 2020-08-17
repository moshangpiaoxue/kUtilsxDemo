package com.mo.kutilsx.modle;

import com.mo.libsx.modle.bean.CityBean;
import com.mo.libsx.utils.sort_utils.SortUtli;

import org.json.JSONArray;

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

    public static List<String> getAllCities() {
        List<String> cityList = new ArrayList<String>();
        //        String cityString = MyShareUtil.getSharedString(context, "cityList");
        String cityString = "[\"全国\",\"广州\",\"深圳\",\"东莞\",\"惠州\",\"珠海\",\"汕头\",\"佛山\",\"中山\",\"南宁\",\"杭州\",\"厦门\",\"长沙\",\"重庆\",\"成都\"]";
        try {
            JSONArray jsonArray = new JSONArray(cityString);
            for (int i = 0; i < jsonArray.length(); i++) {
                cityList.add(jsonArray.get(i).toString());
            }
            return cityList;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static List<CityBean> loadIndexModelData() {
        List<CityBean> data = new ArrayList<>();
        data.add(new CityBean("@@"));
        data.add(new CityBean("vvvv"));
        data.add(new CityBean("##"));
        data.add(new CityBean("安阳"));
        data.add(new CityBean("鞍山"));
        data.add(new CityBean("保定"));
        data.add(new CityBean("包头"));
        data.add(new CityBean("北京"));
        data.add(new CityBean("河北"));
        data.add(new CityBean("北海"));
        data.add(new CityBean("安庆"));
        data.add(new CityBean("伊春"));
        data.add(new CityBean("宝鸡"));
        data.add(new CityBean("本兮"));
        data.add(new CityBean("滨州"));
        data.add(new CityBean("常州"));
        data.add(new CityBean("常德"));
        data.add(new CityBean("常熟"));
        data.add(new CityBean("枣庄"));
        data.add(new CityBean("内江"));
        data.add(new CityBean("阿坝州"));
        data.add(new CityBean("丽水"));
        data.add(new CityBean("成都"));
        data.add(new CityBean("承德"));
        data.add(new CityBean("沧州"));
        data.add(new CityBean("重庆"));
        data.add(new CityBean("东莞"));
        data.add(new CityBean("扬州"));
        data.add(new CityBean("甘南州"));
        data.add(new CityBean("大庆"));
        data.add(new CityBean("佛山"));
        data.add(new CityBean("广州"));
        data.add(new CityBean("合肥"));
        data.add(new CityBean("海口"));
        data.add(new CityBean("济南"));
        data.add(new CityBean("兰州"));
        data.add(new CityBean("南京"));
        data.add(new CityBean("泉州"));
        data.add(new CityBean("荣成"));
        data.add(new CityBean("三亚"));
        data.add(new CityBean("上海"));
        data.add(new CityBean("汕头"));
        data.add(new CityBean("天津"));
        data.add(new CityBean("武汉"));
        data.add(new CityBean("厦门"));
        data.add(new CityBean("宜宾"));
        data.add(new CityBean("张家界"));

        data.add(new CityBean("自贡"));
        data.add(new CityBean("热门"));
        data.add(new CityBean("定位"));


        return SortUtli.CitySort(data);
    }

    public static List<CityBean> getHotCity() {
        List<CityBean> mCities = new ArrayList<>();
        mCities.add(new CityBean("全国"));
        mCities.add(new CityBean("广州"));
        mCities.add(new CityBean("深圳"));
        mCities.add(new CityBean("东莞"));
        mCities.add(new CityBean("惠州"));
        mCities.add(new CityBean("杭州"));
        mCities.add(new CityBean("重庆"));
        mCities.add(new CityBean("厦门"));
        mCities.add(new CityBean("成都"));
        mCities.add(new CityBean("长沙"));
        mCities.add(new CityBean("珠海"));
        mCities.add(new CityBean("汕头"));
        mCities.add(new CityBean("佛山"));
        mCities.add(new CityBean("中山"));
        mCities.add(new CityBean("南宁"));
        return mCities;
    }


    public static List<String> getToc() {
        List<CityBean> list = loadIndexModelData();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list1.add(list.get(i).getTopc());
        }
        return list1;
    }


}
