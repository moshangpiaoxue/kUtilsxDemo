package com.mo.kutilsx.activitys.widget.list;

import android.view.View;
import android.widget.TextView;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.adapter.KBaseRecycleViewAdapter;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.utils.tips_utils.LogUtil;
import com.mo.libsx.utils.viewUtil.ViewUtil;
import com.mo.libsx.view.recycle_view.KRecycleView;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：RecycleView
 */
public class RecycleViewsActivity2 extends BaseActivity implements TitleBarAction {

    private KRecycleView krv_rv;
    List<String> list;
    private KBaseRecycleViewAdapter<String> adapter2;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_recycle3;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rv_kong:
                adapter2.showEmpty();
//                adapter2.refreshView(null);
                break;
            case R.id.tv_pull_zhong:
                adapter2.showLoading();
                break;
            case R.id.tv_rv_cuo:
                adapter2.showError();
                break;
            case R.id.tv_rv_add:
                List<String> list1 = new ArrayList<>();
                list1.add("追加");
                LogUtil.i(list1.toString());
                adapter2.loadMore(list1);
                break;
            case R.id.tv_pull_hao:
                adapter2.loadList(list);
                break;
            case R.id.tv_pull_tou:
                adapter2.addHeaderView(ViewUtil.getView(getActivity(),R.layout.item_main2));
                break;
            case R.id.tv_pull_wei:
                adapter2.addFooterView(ViewUtil.getView(getActivity(),R.layout.item_main3));
                break;
        }
    }

    @Override
    protected void initView() {
        setTitle("RecycleView");

        krv_rv = findViewById(R.id.krv_rv);

        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("普通加载数据：aaaaa" + i);
        }
        //        krv_rv.setAdapter(adapter2 = new KBaseRecycleViewAdapter<String>(getActivity(), list) {
        krv_rv.setAdapter(adapter2 = new KBaseRecycleViewAdapter<String>(getActivity(), null) {

            //            @Override
            //            public int getItemViewType(int position) {
            //                return super.getItemViewType(position);
            //            }
            //            @Override
            //            public int getItemViewType(int position) {
            //                return position == 0 ? R.layout.item_tv : R.layout.item_main;
            //            }

            @Override
            public void doWhat(KRecycleViewHolder holder, String bean, int position, int itemViewType) {
                if (itemViewType == 0) {
                    TextView tv_item_tv = holder.getView(R.id.tv_item_tv);
                    tv_item_tv.setText(bean);
                } else {
                    TextView tv_item_main = holder.getView(R.id.tv_item_main);
                    tv_item_main.setText(bean);
                }

            }

            @Override
            public int getItemLayout(int viewType) {
                return viewType == 0 ? R.layout.item_tv : R.layout.item_main;
            }

        });
    }

    @Override
    protected void initData() {

    }

}
