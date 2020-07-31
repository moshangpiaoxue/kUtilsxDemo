package com.mo.kutilsx.activitys.widget.text_view;

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
 * @ 功能：TextView相关
 */
public class TextViewsActivity extends BaseActivity implements TitleBarAction, ListAction<MainBean> {

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
        setTitle("TextView相关");
        getRecycleView().setLayoutGrid(4);
        setAdapter();
    }

    @Override
    protected void initData() {

    }


    @Override
    public List<MainBean> getList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("旋转TextView", SlantedTextViewActivity.class));
        list.add(new MainBean("圆角TextView", CornerTextViewActivity.class));
        list.add(new MainBean("展开/收缩TextView", ExpandableTextViewActivity.class));
        list.add(new MainBean("提示TextView", BadgeTextViewActivity.class));
        list.add(new MainBean("星球大战样式滚动TextView", CreditsRollTextViewActivity.class));
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
