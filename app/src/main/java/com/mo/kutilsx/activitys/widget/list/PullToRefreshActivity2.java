package com.mo.kutilsx.activitys.widget.list;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mo.kutilsx.R;
import com.mo.libsx.base.adapter.KBaseRecycleViewAdapter;
import com.mo.libsx.base.ui.BaseListActivity;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.utils.tips_utils.LogUtil;
import com.mo.libsx.utils.viewUtil.ViewUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：下拉刷新
 */
public class PullToRefreshActivity2 extends BaseListActivity<String> {

    List<String> list = new ArrayList<>();
    @Override
    protected KBaseRecycleViewAdapter<String> getAdapter() {
        return new KBaseRecycleViewAdapter<String>(getActivity(), list) {
            @Override
            public void doWhat(KRecycleViewHolder holder, String bean, int position, int itemViewType) {
                TextView tv_item_tv = holder.getView(R.id.tv_item_tv);
                tv_item_tv.setText(bean);
            }

            @Override
            public int getItemLayout(int viewType) {
                return R.layout.item_tv;
            }
        };
    }


    @Override
    protected void getMore(int page) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showToast("上完");
                List<String> more = new ArrayList<>();
                for (int i = 0; i < 1; i++) {
                    more.add("上拉加载数据：aaaaa" + i);
                }
                loadMorer(more);
            }
        }, 1000);
    }

    @Override
    protected void getList(int page) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showToast("下完");
//                List<String> load = new ArrayList<>();
                for (int i = 0; i < 1; i++) {
                    list.add(0, "下拉加载数据：aaaaa" + i);
                }
                LogUtil.i("SSSSSS   "+ list.size());
                loadList(list);
            }
        }, 1000);
    }

    @Override
    protected void initData() {
        setTitle("下拉刷新");
        kptrl_base_list_layout.setCanLoadMore(true);
        kptrl_base_list_layout.setCanRefresh(true);
        getList(1);
        View view4 = ViewUtil.getView(getActivity(), R.layout.layout_pull_add, kptrl_base_list_layout);
//        LinearLayout ll_item_main=view4.findViewById(R.id.ll_item_main);
//        ll_item_main.setGravity(Gravity.BOTTOM|Gravity.RIGHT);
        kptrl_base_list_layout.addView(view4);
    }

}
