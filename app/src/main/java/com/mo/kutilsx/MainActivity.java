package com.mo.kutilsx;

import com.mo.kutilsx.action.ListAction;
import com.mo.libsx.action.LoadTypeAction;
import com.mo.libsx.action.TitleBarAction;
import com.mo.kutilsx.activitys.animation_effect.AnimationEffectsActivity;
import com.mo.kutilsx.activitys.function.FunctionsActivity;
import com.mo.kutilsx.activitys.widget.WidgetsActivity;
import com.mo.kutilsx.bean.MainBean;
import com.mo.kutilsx.modle.AdapterModle;
import com.mo.libsx.base.adapter.KRecyclerAdapter;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.utils.viewUtil.ViewUtil;
import com.mo.libsx.view.recycle_view.KRecycleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ListAction<MainBean> ,  LoadTypeAction {

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
        setTitle("主页");
        getRecycleView().setLayoutGrid(4);
        setAdapter();
//        getLoadTypeView().setLoadingView(ViewUtil.getView(getActivity(), com.mo.libsx.R.layout.base_error));
//        getLoadTypeView().showLoading();
//        getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadSuccess();
//            }
//        },2000);
    }

    @Override
    protected void initData() {

    }


    @Override
    public List<MainBean> getList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("控件widget", WidgetsActivity.class));
        list.add(new MainBean("动画效果", AnimationEffectsActivity.class));
        list.add(new MainBean("功能", FunctionsActivity.class));
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
