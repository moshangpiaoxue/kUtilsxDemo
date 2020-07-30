package com.mo.kutilsxdemo.activitys.widget.text_view;

import com.mo.kutilsxdemo.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：圆角TextView
 */
public class CornerTextViewActivity extends BaseActivity implements TitleBarAction {


    @Override
    protected int getLayoutId() {
        return R.layout.act_view_textview_corner;
    }

    @Override
    protected void initView() {
        setTitle("圆角TextView");
    }

    @Override
    protected void initData() {

    }

}
