package com.mo.kutilsxdemo.activitys.widget.progress_bar;

import com.mo.kutilsxdemo.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.view.progressbar.RoundProgressBar;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：ProgressBar相关
 */
public class ProgressBarsActivity extends BaseActivity implements TitleBarAction {


    @Override
    protected int getLayoutId() {
        return R.layout.act_view_progressbar;
    }

    @Override
    protected void initView() {
        setTitle("ProgressBar相关");
        ((RoundProgressBar)findViewById(R.id.rpb_pro)).setProgress(50);
    }

    @Override
    protected void initData() {

    }

}
