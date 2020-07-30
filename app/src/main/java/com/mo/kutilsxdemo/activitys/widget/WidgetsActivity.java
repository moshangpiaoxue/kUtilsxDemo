package com.mo.kutilsxdemo.activitys.widget;

import com.mo.kutilsxdemo.R;
import com.mo.kutilsxdemo.action.ListAction;
import com.mo.libsx.action.TitleBarAction;
import com.mo.kutilsxdemo.activitys.widget.list.ListsActivity;
import com.mo.kutilsxdemo.activitys.widget.text_view.TextViewsActivity;
import com.mo.kutilsxdemo.activitys.widget.edit_view.EditsViewActivity;
import com.mo.kutilsxdemo.activitys.widget.image_view.ImagesViewActivity;
import com.mo.kutilsxdemo.activitys.widget.progress_bar.ProgressBarsActivity;
import com.mo.kutilsxdemo.activitys.widget.view_switcher.ViewSwitcherActivity;
import com.mo.kutilsxdemo.activitys.widget.viewpage.ViewPagesActivity;
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
 * @ 功能：控件界面
 */
public class WidgetsActivity extends BaseActivity implements TitleBarAction, ListAction<MainBean> {

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
        setTitle("控件widget");
        getRecycleView().setLayoutGrid(4);
        setAdapter();
    }

    @Override
    protected void initData() {

    }

    @Override
    public List<MainBean> getList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("TextView相关", TextViewsActivity.class));
        list.add(new MainBean("EdittView相关", EditsViewActivity.class));
        list.add(new MainBean("ImageView相关", ImagesViewActivity.class));
        list.add(new MainBean("ProgressBar相关", ProgressBarsActivity.class));
        list.add(new MainBean("列表相关", ListsActivity.class));
        list.add(new MainBean("ViewSwitcher相关", ViewSwitcherActivity.class));
        list.add(new MainBean("ViewPage相关", ViewPagesActivity.class));
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
