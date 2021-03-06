package com.mo.kutilsx.activitys.widget.list;

import com.mo.kutilsx.R;
import com.mo.kutilsx.action.ListAction;
import com.mo.libsx.action.TitleBarAction;
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
 * @ 功能：列表相关
 */
public class ListsActivity extends BaseActivity implements TitleBarAction, ListAction<MainBean> {

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
        setTitle("列表相关");
        getRecycleView().setLayoutGrid(4);
        setAdapter();
    }

    @Override
    protected void initData() {

    }


    @Override
    public List<MainBean> getList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("RecycleView", RecycleViewsActivity.class));
        list.add(new MainBean("RecycleView2", RecycleViewsActivity2.class));
        list.add(new MainBean("自动换行RecycleView", RvAutoLineBreakActivity.class));
        list.add(new MainBean("下拉刷新", PullToRefreshActivity.class));
        list.add(new MainBean("下拉刷新2", PullToRefreshActivity2.class));
        list.add(new MainBean("下拉刷新", AutoLocateHorizontalViewActivity.class));
        list.add(new MainBean("选择城市", CityPickerActivity2.class));
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
