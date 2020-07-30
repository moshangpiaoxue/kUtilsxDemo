package com.mo.kutilsxdemo.activitys.animation_effect.bubble;

import com.mo.kutilsxdemo.R;
import com.mo.kutilsxdemo.action.ListAction;
import com.mo.libsx.action.TitleBarAction;
import com.mo.kutilsxdemo.bean.MainBean;
import com.mo.kutilsxdemo.modle.AdapterModle;
import com.mo.libsx.base.adapter.KRecyclerAdapter;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.view.recycle_view.KRecycleView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/25:16:57
 * @ 功能：气泡动效
 */
public class BubblesActivity extends BaseActivity implements TitleBarAction, ListAction<MainBean> {

    @Override
    protected boolean isStatusBarEnabled() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setTitle("气泡动效");
        getRecycleView().setLayoutGrid(4);
        setAdapter();
    }

    @Override
    protected void initData() {

    }

    @Override
    public List<MainBean> getList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("气泡从水底升起", CircleBubbleActivity.class));
        list.add(new MainBean("气泡从水底升起-指定气泡图片", IconBubbleActivity.class));
        return list;
    }

    @Override
    public KRecycleView getRecycleView() {
        return findViewById(R.id.krv_main);
    }

    @Override
    public KRecyclerAdapter getWrapRecyclerAdapter() {
        return AdapterModle.getMainAdapter(getActivity(), getList());
    }
}
