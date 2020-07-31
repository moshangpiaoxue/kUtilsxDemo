package com.mo.kutilsx.activitys.widget.view_switcher;

import android.widget.TextSwitcher;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.utils.viewUtil.TextSwitcherUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/29:13:56
 * @ 功能：ViewSwitcher相关
 */
public class ViewSwitcherActivity extends BaseActivity implements TitleBarAction {


    @Override
    protected int getLayoutId() {
        return R.layout.act_view_textswitcher;
    }

    @Override
    protected void initView() {
        setTitle("ViewSwitcher相关");
        TextSwitcher ts_viewswitcher = findViewById(R.id.ts_viewswitcher);
        List<String> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("TextSwitcher="+i);
        }
        TextSwitcherUtil textSwitcherUtil = new TextSwitcherUtil(ts_viewswitcher, list);
        textSwitcherUtil.create();
    }

    @Override
    protected void initData() {

    }
}
