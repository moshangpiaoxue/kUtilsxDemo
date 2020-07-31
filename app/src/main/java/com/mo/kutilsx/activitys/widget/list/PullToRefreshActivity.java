package com.mo.kutilsx.activitys.widget.list;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.adapter.BaseRecycleViewAdapter;
import com.mo.libsx.base.adapter.KRecyclerAdapter;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.view.PullToRefresh.BaseRefreshListener;
import com.mo.libsx.view.PullToRefresh.KPullToRefreshLayout;
import com.mo.libsx.view.PullToRefresh.ViewStatus;
import com.mo.libsx.view.recycle_view.KRecycleView;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：下拉刷新
 */
public class PullToRefreshActivity extends BaseActivity implements TitleBarAction {

    private KPullToRefreshLayout kpl_tv;
    private KRecycleView krv_pull;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_pull_to_refresh;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pull_kong:
                kpl_tv.showView(ViewStatus.EMPTY_STATUS);
                break;
            case R.id.tv_pull_cuo:
                kpl_tv.showView(ViewStatus.ERROR_STATUS);
                break;
            case R.id.tv_pull_zhong:
                kpl_tv.showView(ViewStatus.LOADING_STATUS);
                break;
            case R.id.tv_pull_hao:
                kpl_tv.showView(ViewStatus.CONTENT_STATUS);
                break;
        }
    }

    @Override
    protected void initView() {
        setTitle("下拉刷新");

        kpl_tv = findViewById(R.id.kpl_tv);
        krv_pull = findViewById(R.id.krv_pull);
        kpl_tv.setCanRefresh(true);
        kpl_tv.setCanLoadMore(true);
//        kpl_tv.autoRefresh();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("普通加载数据：aaaaa"+i);
        }
        krv_pull.setAdapter(new KRecyclerAdapter(new BaseRecycleViewAdapter<String>(getActivity(), list) {
            @Override
            public void doWhat(KRecycleViewHolder holder, String bean, int position, int itemViewType, RecyclerView mRecyclerView) {
                TextView tv_item_tv = holder.getView(R.id.tv_item_tv);
                tv_item_tv.setText(bean);
            }

            @Override
            public int getItemLayout(int viewType) {
                return R.layout.item_tv;
            }
        }));
        kpl_tv.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showToast("下完");
                        for (int i = 0; i < 5; i++) {
                            list.add(0,"下拉加载数据：aaaaa"+i);
                        }
                        krv_pull.getAdapter().notifyDataSetChanged();
                        kpl_tv.finishRefresh();
                    }
                }, 1000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showToast("上完");
                        for (int i = 0; i < 5; i++) {
                            list.add("上拉加载数据：aaaaa"+i);
                        }
                        krv_pull.getAdapter().notifyDataSetChanged();
                        kpl_tv.finishLoadMore();
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void initData() {

    }

}
