package com.mo.kutilsx.activitys.widget.edit_view;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：EdittView相关
 */
public class EditsViewActivity extends BaseActivity implements TitleBarAction {


    @Override
    protected int getLayoutId() {
        return R.layout.act_view_editsview;
    }

    @Override
    protected void initView() {
        setTitle("EdittView相关");
    }

    @Override
    protected void initData() {

    }
}
