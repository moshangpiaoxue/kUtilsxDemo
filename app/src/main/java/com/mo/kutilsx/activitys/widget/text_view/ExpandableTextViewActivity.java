package com.mo.kutilsx.activitys.widget.text_view;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：展开/收缩TextView
 */
public class ExpandableTextViewActivity extends BaseActivity implements TitleBarAction {


    @Override
    protected int getLayoutId() {
        return R.layout.act_view_textview_expandable;
    }

    @Override
    protected void initView() {
        setTitle("展开/收缩TextView");
    }

    @Override
    protected void initData() {

    }

}
