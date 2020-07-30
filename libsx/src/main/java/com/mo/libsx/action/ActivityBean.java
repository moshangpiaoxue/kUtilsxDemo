package com.mo.libsx.action;

/**
 * @ author：mo
 * @ data：2020/7/30:11:12
 * @ 功能：
 */
public class ActivityBean {
    private   int mCurrentTabInde = -1;

    public ActivityBean(int mCurrentTabInde) {
        this.mCurrentTabInde = mCurrentTabInde;
    }

    public int getmCurrentTabInde() {
        return mCurrentTabInde;
    }

    public void setmCurrentTabInde(int mCurrentTabInde) {
        this.mCurrentTabInde = mCurrentTabInde;
    }
}
