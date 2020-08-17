package com.mo.libsx.base.ui;

import android.widget.LinearLayout;

import com.mo.libsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.adapter.KBaseRecycleViewAdapter;
import com.mo.libsx.view.PullToRefresh.BaseRefreshListener;
import com.mo.libsx.view.PullToRefresh.KPullToRefreshLayout;
import com.mo.libsx.view.recycle_view.KRecycleView;

import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/31:13:43
 * @ 功能：
 */
public abstract class BaseListActivity<T> extends BaseActivity implements TitleBarAction {
    /** 整体布局，可在列表数据外添加头尾布局，在实现title意图接口的时候首先默认在0的位置上add了一个titleview */
    protected LinearLayout ll_base_list_main;
    /** 下拉刷新控件 (这个控件本身是个帧布局，也就是说当有需求的时候也可以往里addview) */
    protected KPullToRefreshLayout kptrl_base_list_layout;
    /** rv */
    protected KRecycleView krv_base_list_layout;
    /** 页面 默认1 */
    protected int mPage = 1;
    /** 适配器 */
    protected KBaseRecycleViewAdapter<T> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.base_list_layout;
    }

    @Override
    protected void initView() {
        ll_base_list_main = findViewById(R.id.ll_base_list_main);
        kptrl_base_list_layout = findViewById(R.id.kptrl_base_list_layout);
        krv_base_list_layout = findViewById(R.id.krv_base_list_layout);

        //默认不可以下拉刷新上拉加载更多
        kptrl_base_list_layout.setCanRefresh(false);
        kptrl_base_list_layout.setCanLoadMore(false);

        //设置适配器
        krv_base_list_layout.setAdapter(mAdapter = getAdapter());

        //设置刷新监听
        kptrl_base_list_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                getList(mPage = 1);
            }

            @Override
            public void loadMore() {
                getMore(mPage++);
            }
        });

        //是否开启自动刷新（首次进入的时候是否直接先走一个下拉刷新）
        if (isCanAutoRefresh()) {
            kptrl_base_list_layout.autoRefresh();
        }
    }

    /**
     * 刷新数据
     */
    public void loadList(List<T> list) {
        if (list != null && list.size() != 0) {
            mAdapter.loadList(list);
            mPage++;

        } else {
            mAdapter.loadList(null);
        }
        kptrl_base_list_layout.finishRefresh();
    }

    /**
     * 加载更多
     *
     * @param list
     */
    public void loadMorer(List<T> list) {
        if (list != null && list.size() != 0) {
            mAdapter.loadMore(list);
        } else {
            mPage -= 1;
            showToast("没有更多数据");
            kptrl_base_list_layout.setCanLoadMore(false);
        }
        kptrl_base_list_layout.finishLoadMore();
    }


    protected boolean isCanAutoRefresh() {
        return false;
    }

    protected abstract KBaseRecycleViewAdapter<T> getAdapter();

    protected abstract void getMore(int page);

    protected abstract void getList(int page);


}
