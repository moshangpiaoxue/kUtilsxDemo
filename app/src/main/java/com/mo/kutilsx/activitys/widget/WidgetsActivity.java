package com.mo.kutilsx.activitys.widget;

import com.mo.kutilsx.R;
import com.mo.kutilsx.action.ListAction;
import com.mo.kutilsx.activitys.widget.data_picker.DataPickerActivity;
import com.mo.kutilsx.activitys.widget.popwindow.PopWindowsActivity;
import com.mo.libsx.action.TitleBarAction;
import com.mo.kutilsx.activitys.widget.list.ListsActivity;
import com.mo.kutilsx.activitys.widget.text_view.TextViewsActivity;
import com.mo.kutilsx.activitys.widget.edit_view.EditsViewActivity;
import com.mo.kutilsx.activitys.widget.image_view.ImagesViewActivity;
import com.mo.kutilsx.activitys.widget.progress_bar.ProgressBarsActivity;
import com.mo.kutilsx.activitys.widget.view_switcher.ViewSwitcherActivity;
import com.mo.kutilsx.activitys.widget.viewpage.ViewPagesActivity;
import com.mo.kutilsx.bean.MainBean;
import com.mo.kutilsx.modle.AdapterModle;
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
        list.add(new MainBean("PopWindow相关", PopWindowsActivity.class));
        list.add(new MainBean("日期选择相关", DataPickerActivity.class));
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
