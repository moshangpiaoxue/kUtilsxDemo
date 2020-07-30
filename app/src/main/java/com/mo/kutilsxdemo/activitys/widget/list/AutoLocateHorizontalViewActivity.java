package com.mo.kutilsxdemo.activitys.widget.list;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mo.kutilsxdemo.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.adapter.BaseRecycleViewAdapter;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.view.recycle_view.AutoLocateHorizontalView;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：RecycleView
 */
public class AutoLocateHorizontalViewActivity extends BaseActivity implements TitleBarAction {

    private AutoLocateHorizontalView alhrv;
    List<String> list;
    private adaptrt1 adapter2;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_auto_hor_recycle;
    }

    //    @Override
    //    public void onClick(View v) {
    //        switch (v.getId()) {
    //            case R.id.tv_rv_kong:
    //                list = new ArrayList<String>();
    //                adapter2.notifyDataSetChanged1();
    //                break;
    //            case R.id.tv_rv_add:
    //                list.add("追加");
    //                adapter2.notifyDataSetChanged1();
    //                break;
    //            case R.id.tv_pull_zhong:
    //                break;
    //            case R.id.tv_pull_hao:
    //                break;
    //        }
    //    }

    @Override
    protected void initView() {
        setTitle("RecycleView(数据变化有问题，一般使用无碍)");

        alhrv = findViewById(R.id.alhrv);

        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("普通加载数据：aaaaa" + i);
        }
        alhrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        alhrv.setAdapter(adapter2 = new adaptrt1(getActivity(), list));
    }

    @Override
    protected void initData() {

    }


    public class adaptrt1 extends BaseRecycleViewAdapter<String> implements AutoLocateHorizontalView.IAutoLocateHorizontalView {
        /**
         * 构造方法
         *
         * @param context
         * @param datas
         */
        public adaptrt1(Context context, List<String> datas) {
            super(context, datas);
        }

        View iv;

        @Override
        public void doWhat(KRecycleViewHolder holder, String bean, int position, int itemViewType, RecyclerView mRecyclerView) {
            iv = holder.getRootView();
            TextView tv_item_tv = holder.getView(R.id.tv_item_tv);
            tv_item_tv.setText(bean);
        }

        @Override
        public int getItemLayout(int viewType) {
            return R.layout.item_tv;
        }

        @Override
        public View getItemView() {
            return iv;
        }

        @Override
        public void onViewSelected(boolean isSelected, int pos, RecyclerView.ViewHolder holder, int itemWidth) {

        }
    }

    {

    }
}
