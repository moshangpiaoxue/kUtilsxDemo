package com.mo.kutilsx.bean;

import com.mo.kutilsx.R;

/**
 * @ author：mo
 * @ data：2020/7/25:15:56
 * @ 功能：
 */
public class MainBean {
    private String title;
    private int dwrableId= R.mipmap.aa;
    private Class activity;

    public MainBean(String title, Class activity) {
        this.title = title;
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "MainBean{" +
                "title='" + title + '\'' +
                ", dwrableId=" + dwrableId +
                ", activity=" + activity +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDwrableId() {
        return dwrableId;
    }

    public void setDwrableId(int dwrableId) {
        this.dwrableId = dwrableId;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

}
